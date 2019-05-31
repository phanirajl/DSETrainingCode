package com.datastax.enablement.analytics.mllib

import scala.collection.mutable

import org.apache.log4j.{ Level, Logger }

import org.apache.spark.{ SparkConf, SparkContext }
import org.apache.spark.mllib.recommendation.{ ALS, MatrixFactorizationModel, Rating }
import org.apache.spark.sql.SparkSession
import org.apache.spark.rdd.RDD

/**
 * An example app for ALS on MovieLens data (http://grouplens.org/datasets/movielens/).
 * Data set used is the ratings.csv, a tar'd copy of this file can be found at:
 * https://drive.google.com/file/d/14-SaMBQ-uNqc9YK26--PbPasuIksooB_/
 *
 * to run the app you first have to create a directory in dsefs and load the file
 * 'dse fs'
 * 'mkdir -p mllib/movieLens'
 * 'cd mllib/movieLens'
 * 'put ./ratings.csv ratings.csv'
 *
 */
object CollabFilter {

  def main(args: Array[String]) {
    filter();
  }

  def filter() {
    // settings for how you want to train
    val numIterations = 20
    val lambda = 1
    val rank = 10
    val numUserBlocks = -1
    val numProductBlocks = -1
    val implicitPrefs = false

    // can't run this in the IDE for DSE 6.0.0-EAP1 as libraries needed are not available to pull in via POM
    // build the jar and then run from your project directory with the follow command 
    // dse spark-submit --class com.datastax.enablement.analytics.mllib.CollabFilter target/dse-enablement-scala-0.0.1-SNAPSHOT.jar
    // note the setting of more memory needed as defaults give me on my mac an OOM
    val conf = new SparkConf(true).setAppName("CollabFilter Using ALS")
      .set("spark.driver.memory", "2g").set("spark.executor.memory", "2g")

    val spark: SparkSession = SparkSession.builder().config(conf).enableHiveSupport().getOrCreate();
    // this is a running example of reading from DSEFS
    val movieRatings = spark.read.format("csv").option("header", "true").load("dsefs:///mllib/movieLens/ratings.csv")

    // need to import the implicits so we can map the strings to number values
    import spark.implicits._

    // since we imported with headers we could use that to get the field rather than the index, but the index is safe so why not
    // caching the rating info since we use in a couple of places and don't want to redo the work
    val ratings = movieRatings.map { line => (Rating(line.getString(0).toInt, line.getString(1).toInt, line.getString(2).toDouble)) }.cache()

    val numRatings = ratings.count()
    val numUsers = ratings.map(_.user).distinct().count()
    val numMovies = ratings.map(_.product).distinct().count()

    println(s"Got $numRatings ratings from $numUsers users on $numMovies movies.")

    // split the data set randomly so we have both training data and test data to see how well we predict
    val splits = ratings.randomSplit(Array(0.8, 0.2))
    val training = splits(0).cache()
    val test = splits(1)

    val numTraining = training.count()
    val numTest = test.count()
    println(s"Training: $numTraining, test: $numTest.")

    // get rid of cache since we no longer need
    ratings.unpersist(blocking = false)

    // now we build the model using the settings we specified.  For a real job we would play with numbers and 
    // see how to best model our recommendation system (keep lowering our error)
    // of course over time we might want to retrain as new data comes in
    // see https://spark.apache.org/docs/preview/api/scala/index.html#org.apache.spark.mllib.recommendation.ALS
    // to understand what all we are setting
    val model = new ALS().setRank(rank)
      .setIterations(numIterations)
      .setLambda(lambda)
      .setImplicitPrefs(implicitPrefs)
      .setUserBlocks(numUserBlocks)
      .setProductBlocks(numProductBlocks)
      .run(training.rdd)
    // in the above we had to convert training to rdd, with spark 2.2 (aka if I had the latest libraries) 
    // I am thinking/hoping we could use just DataFrames

    // get our error rate 
    val rmse = computeRmse(model, test.rdd, false)

    // This is our standard deviation, lower means we did a better job with our recommendations
    println("Ratings are on a scale of 1 -5")
    println(s"Test Root Mean Squared Error = $rmse.")

    spark.stop()
    return
  }

  /** Compute RMSE (Root Mean Squared Error). */
  def computeRmse(model: MatrixFactorizationModel, data: RDD[Rating], implicitPrefs: Boolean): Double = {

    def mapPredictedRating(r: Double): Double = {
      if (implicitPrefs) math.max(math.min(r, 1.0), 0.0) else r
    }

    val predictions: RDD[Rating] = model.predict(data.map(x => (x.user, x.product)))
    val predictionsAndRatings = predictions.map { x =>
      ((x.user, x.product), mapPredictedRating(x.rating))
    }.join(data.map(x => ((x.user, x.product), x.rating))).values
    math.sqrt(predictionsAndRatings.map(x => (x._1 - x._2) * (x._1 - x._2)).mean())
  }
}