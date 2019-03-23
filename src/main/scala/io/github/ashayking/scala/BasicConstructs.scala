package io.github.ashayking.scala

object BasicConstructs {

  def main(args: Array[String]): Unit = {

    // expression
    val a = {
      val b = 10
      b * b
    }
    println(a)

    // If-else
    val i = 10
    val j = 20
    if(i>j) {
      println(i)
    }else if(i==j){
      println("Equal")
    }else {
      println(j)
    }

    // ternary
    val res = if(i>j) i else j
    println(res)

    // switch
    val x = 10
    val y = 5
    val res1 = x > y match {
      case true => x
      case false => y
      //case x if(x==10) => "UNKNOWN"
     // case _ => "HEY"
    }
    println(res1)

//    // while loop
//    var ctr=0
//    while(ctr<10) {
//      print(s"$ctr ->")
//      ctr=ctr+1
//    }

    // for with by
    for(i <- 1 to 7 by 2){
      println(i)
    }

    for(i <- 1 until 7){
      println(i)
    }

    for(i <- 10 to 1 by -1){
      println(i)
    }

    // Using yield
    val resF = for (i <- 1 to 5) yield i * 2
    print(resF)

    val arr = Array("Scala","Java","Python")
    val c = for (i <- arr) yield {
      i(0)
    }
    c.foreach(print)
  }


}
