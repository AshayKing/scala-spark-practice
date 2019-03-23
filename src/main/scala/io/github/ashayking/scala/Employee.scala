package io.github.ashayking.scala

case class Employee(id: Int, name: String){
  override def toString = s"Employee [ $id:$name ]" 
}

object EmployeeApp {
  
  def main(args: Array[String]): Unit = {
    // Case class :
    // Serializable
    // Have getter & setter
    // hashcode toString copy method
    // fields is implicitly private [no need to specify val]
    val empExplicit = new Employee(1,"ASHAY")
    println(empExplicit.name)
    
    val empImplicit = Employee(1,"ASHAY")
    println(empImplicit.name)
    // productIterator for all atts values
    empExplicit.productIterator.foreach(println)
    println(empExplicit.productElement(1))
    println(empImplicit)
  }
}