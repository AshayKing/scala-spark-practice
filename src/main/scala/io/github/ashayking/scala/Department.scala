package io.github.ashayking.scala

/**
 * This is example of Companion class & object
 */
class Department(val departmentId: Int, val departmentName: String) {

  require(departmentId > 0, "DepartmentId must be greater than 0")

  def this(departmentId: Int) = {
    this(departmentId, "HR")
    println("Something extra")
  }

  override def toString() = s"Department [ $departmentId, $departmentName ]"
  
}

object Department {
  
  def staticLikeMethod(): String = {
    "Static method content"
  }

  def main(args: Array[String]): Unit = {
    val d = new Department(1);
    println(d)
    println(Department.staticLikeMethod);
  }

}