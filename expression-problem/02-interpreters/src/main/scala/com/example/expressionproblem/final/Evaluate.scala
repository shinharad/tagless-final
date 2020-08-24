package com.example
package expressionproblem
package `final`

object Evaluate {
  object Expression {
    val dsl: Expression[Option, Int] =
      new Expression[Option, Int] {
        override def literal(n: Int): Option[Int] =
          Some(n)

        override def negate(a: Option[Int]): Option[Int] =
          a.map(-_)

        override def add(a1: Option[Int], a2: Option[Int]): Option[Int] =
          a1.zip(a2).map {
            case (a1, a2) => a1 + a2
          }
      }
  }

  object Multiplication {
    val dsl: Multiplication[Option, Int] =
      new Multiplication[Option, Int] {
        override def multiply(a1: Option[Int], a2: Option[Int]): Option[Int] =
          a1.zip(a2).map {
            case (a1, a2) => a1 * a2
          }
      }
  }

  object Division {
    val dsl: Division[Option, Int] =
      new Division[Option, Int] {
        override def divide(a1: Option[Int], a2: Option[Int]): Option[Int] =
          a1.zip(a2).flatMap {
            case (_, 0) =>
              None

            case (a1, a2) =>
              if (a1 % a2 == 0)
                Some(a1 / a2)
              else
                None
          }
      }
  }
}
