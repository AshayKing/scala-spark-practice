package io.github.ashayking.scala

object FunctionsvsMethods {

  // Method : not a object
  def calArea(rad: Double): Double = {
    3.14 * rad * rad
  }

  def getCircleStats(r: Double) = {
    val PI = 3.14
    def getArea(r: Double) = PI * r * r
    def getCircumference(r: Double) = 2 * PI * r

    (getArea(r), getCircumference(r))
  }

  // HOF
  def compareStrDesc(s1: String, s2: String): Int = {
    if (s1 == s2) 0
    else if (s1 > s2) -1
    else 1
  }

  def compareStrAsc(s1: String, s2: String): Int = {
    if (s1 == s2) 0
    else if (s1 < s2) -1
    else 1
  }

  def smartCompare(s1: String, s2: String, cmpFn: (String, String) => Int) = {
    cmpFn(s1, s2)
  }

  // Param grouping
  def product(x: Int, y: Int)(a: Int, b: Int): Int = x * y + a * b

  def main(args: Array[String]): Unit = {
    println(calArea(2))

    // this is object
    val area = (rad: Double) => {
      3.14 * rad * rad
    }: Double
    println(area(2))

    // method to function
    val calAreaFunc = calArea _
    println(calAreaFunc(2))

    // First class function

    // 1. Can be stored as variable
    // 2. Return val can be func
    // 3. Param can be func
    println(getCircleStats(2))

    //HOF
    println(smartCompare("Ashay", "Kiran", compareStrAsc))
    println(smartCompare("Ashay", "Kiran", compareStrDesc))
    
    // Partial Function
    // You dnot know what values => put _
    val partialFunc = product(2, 3)(_, _)
    println(partialFunc(10, 20))
  }
}