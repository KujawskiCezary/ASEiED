package com.jwszol
import org.apache.spark.sql.{Dataset, SparkSession}
import org.apache.spark.sql.functions.col
import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.functions.explode
import org.apache.spark.sql.Column


class SortingJob {

  val sparkSession = SparkSession.builder.
    master("local")
    .appName("spark session example")
    .config("spark.some.config.option", "some-value")
    .getOrCreate()
  val path = "./src/main/resources/dataMay-31-2017.json"
  val MapPartRDD = sparkSession.sparkContext.wholeTextFiles(path).values
  val rawData = sparkSession.read.json(MapPartRDD)
  val extractedPairs = rawData.withColumn("data", explode(rawData.col("data"))).select("data")
  extractedPairs.createOrReplaceTempView("pairs_view")
  val splittedPairs = sparkSession.sql("SELECT data[0] as id, data[1] as value FROM pairs_view")

  def selectionSort: Unit = {
    //rawData.printSchema()
    //extractedPairs.show()

    splittedPairs.show()



  }
}

