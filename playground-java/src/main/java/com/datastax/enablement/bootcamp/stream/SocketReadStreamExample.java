package com.datastax.enablement.bootcamp.stream;

import java.util.Arrays;
import java.util.Iterator;

import org.apache.spark.*;
import org.apache.spark.api.java.function.*;
import org.apache.spark.streaming.*;
import org.apache.spark.streaming.api.java.*;
import com.datastax.enablement.bootcamp.beans.UserAddress;
import static com.datastax.spark.connector.japi.CassandraStreamingJavaUtil.*;
import static com.datastax.spark.connector.japi.CassandraJavaUtil.*;

public class SocketReadStreamExample {

	public static void main(String[] args) {
		getStream();
	}

	@SuppressWarnings("serial")
	private static void getStream() {

		SparkConf conf = new SparkConf().setMaster("local[4]").setAppName("Java Read Stream")
				.set("spark.cassandra.connection.host", "127.0.0.1").set("spark.driver.memory", "4g")
				.set("spark.driver.cores", "4");
		
		@SuppressWarnings("resource")
		JavaStreamingContext jssc = new JavaStreamingContext(conf, Durations.seconds(5));
		JavaReceiverInputDStream<String> lines = jssc.socketTextStream("localhost", 5006);

		JavaDStream<String> split_lines = lines.flatMap(new FlatMapFunction<String, String>() {
			@Override
			public Iterator<String> call(String x) {
				return Arrays.asList(x.split(",")).iterator();
			}
		});
		
		JavaDStream<UserAddress> user_address = split_lines.flatMap(new FlatMapFunction<String, UserAddress>() {
			@SuppressWarnings("unchecked")
			@Override
			public Iterator<UserAddress> call(String x) {
				return (Iterator<UserAddress>) UserAddress.returnAddress(Arrays.asList(x.split(",")).iterator());
			}
		});
		
		javaFunctions(user_address).writerBuilder("bootcamp", "user_address_multiple", mapToRow(UserAddress.class)).saveToCassandra();
		
		
		// Start the computation
		jssc.start();
		// Wait for the computation to terminate
		try {
			jssc.awaitTermination();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
