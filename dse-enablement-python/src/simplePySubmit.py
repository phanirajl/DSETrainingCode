from pyspark.sql import Row, SQLContext
from pyspark import SparkContext, SparkConf

# simple python code to see a spark-submit

conf = SparkConf().setAppName("simple Python script via spark submit")
sc = SparkContext(conf=conf)
sqlContext = SQLContext(sc)


def getData():
    case = sqlContext.read.format("org.apache.spark.sql.cassandra").options(table="user_address_multiple", keyspace="bootcamp").load()
    case.registerTempTable("user_addresses")
    caseForML = sqlContext.sql("select * from user_addresses")
    caseForML.show()


if __name__ == "__main__":
    getData()