package com.example
package expressionproblem
package `final`

object View {
  object Expression {
    val dsl: Expression[String] =
      new Expression[String] {
        override def literal(n: Int): String =
          s"${n}"

        override def negate(a: String): String =
          s"(-${a})"

        override def add(a1: String, a2: String): String =
          s"(${a1} + ${a2})"
      }
  }

  object Multiplication {
    val dsl: Multiplication[String] =
      new Multiplication[String] {
        override def multiply(a1: String, a2: String): String =
          s"(${a1} * ${a2})"
      }
  }
}
