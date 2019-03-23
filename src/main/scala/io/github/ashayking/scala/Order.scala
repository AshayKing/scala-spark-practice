package io.github.ashayking.scala

case class Order(orderId: Int, orderDate: String, orderCustomerId: Int, orderStatus: String) {
  override def toString = s"Order : $orderId => $orderStatus"
}

object ObjectApp {

  def main(args: Array[String]): Unit = {
    val orderList = List(
      Order(1, "2019-03-01", 100, "COMPLETE"),
      Order(2, "2019-03-01", 20, "COMPLETE"),
      Order(3, "2019-03-01", 490, "PENDING"),
      Order(4, "2019-03-01", 1, "COMPLETE"),
      Order(5, "2019-03-01", 521, "CLOSED"),
      Order(6, "2019-03-01", 312, "COMPLETE"),
      Order(7, "2019-03-01", 511, "PENDING"),
      Order(8, "2019-03-01", 19, "NEW"),
      Order(9, "2019-03-01", 100, "NEW"),
      Order(10, "2019-03-01", 100, "COMPLETE"),
      Order(11, "2019-03-01", 100, "PENDING"),
      Order(12, "2019-03-01", 100, "NEW"),
      Order(13, "2019-03-01", 100, "CLOSED"))

    // Define Function for CompleteOrder
    val completeOrder = (order: Order) => {
      order.orderStatus == "COMPLETE"
    }

    val completedOrders = orderList.filter(completeOrder)
    completedOrders.foreach(println)
    
    // Size
    println(completedOrders.length)

    // map
    completedOrders.map(ele=>ele.orderId).foreach(println)
    
    // Unique customer
    orderList.map(ele=>ele.orderCustomerId).toSet.foreach(println)
    
    // groupby
    orderList.groupBy(o=>o.orderStatus).foreach(println)
    orderList.groupBy(o=>o.orderStatus).map(kv=>(kv._1,kv._2.length)).foreach(println)
    
  }

}