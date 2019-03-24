package io.github.ashayking.spark.sql

import org.apache.spark.sql.SQLContext
import org.apache.spark.SparkConf
import org.apache.spark.SparkContext

object SparkSqlDemo {

  def main(args: Array[String]): Unit = {

    val conf = new SparkConf()
    val sc = new SparkContext("local[2]", "RDDCollections", conf)
    val sqlContext = new SQLContext(sc)
    import sqlContext.implicits._
    import org.apache.spark.sql.functions._

    val ordersData = sc.textFile("/home/ashay/DBDOCKER/data/retail_db/orders")
      .map(rec => {
        val r = rec.split(",")
        Order(r(0).toInt, r(1), r(2).toInt, r(3))
      })

    val ordersItemsData = sc.textFile("/home/ashay/DBDOCKER/data/retail_db/order_items")
      .map(rec => {
        val r = rec.split(",")
        OrderItem(r(0).toInt, r(1).toInt, r(2).toInt, r(3).toInt, r(4).toFloat, r(5).toFloat)
      })
    val orderDF = sqlContext.createDataFrame(ordersData)
    val orderItemDF = sqlContext.createDataFrame(ordersItemsData)

    //orderDF.printSchema()

    //orderDF.select("order_id").take(10).foreach(println)

    val completedOrders = orderDF.filter(orderDF("order_status") === "COMPLETE")

    val orderJoin = completedOrders
      .join(orderItemDF, completedOrders("order_id") === orderItemDF("order_item_order_id"))

   // orderJoin.groupBy("order_date").agg(sum("order_item_subtotal")).show()

    // As SQL
    orderDF.createTempView("order_data")
    sqlContext.sql("SELECT order_status,count(1) FROM order_data GROUP BY order_status")
      .show()
  }

}