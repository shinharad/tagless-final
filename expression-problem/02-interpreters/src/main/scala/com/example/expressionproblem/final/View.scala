package com.example
package expressionproblem
package `final`

object View {
  object Expression {
    val dsl: Expression[Option, String] =
      new Expression[Option, String] {
        override def literal(n: Int): Option[String] =
          Some(s"${n}")

        override def negate(a: Option[String]): Option[String] =
          a.map(a => s"(-${a})")

        override def add(
            a1: Option[String],
            a2: Option[String]
          ): Option[String] =
          a1.zip(a2).map {
            case (a1, a2) =>
              s"(${a1} + ${a2})"
          }

      }
  }

  object Multiplication {
    val dsl: Multiplication[Option, String] =
      new Multiplication[Option, String] {
        override def multiply(
            a1: Option[String],
            a2: Option[String]
          ): Option[String] =
          a1.zip(a2).map {
            case (a1, a2) =>
              s"(${a1} * ${a2})"
          }
      }
  }

  object Division {
    val dsl: Division[Option, String] =
      new Division[Option, String] {
        override def divide(
            a1: Option[String],
            a2: Option[String]
          ): Option[String] =
          a1.zip(a2).map {
            case (a1, a2) =>
              s"(${a1} / ${a2})"
          }
      }
  }
}
