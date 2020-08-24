package com.example
package expressionproblem
package `final`

object Evaluate {
  object Expression {
    val dsl: Expression[Int] =
      new Expression[Int] {
        override def literal(n: Int): Int =
          n

        override def negate(a: Int): Int =
          -a

        override def add(a1: Int, a2: Int): Int =
          a1 + a2
      }
  }

  object Multiplication {
    val dsl: Multiplication[Int] =
      new Multiplication[Int] {
        override def multiply(a1: Int, a2: Int): Int =
          a1 * a2
      }
  }

  object Division {
    val dsl: Division[Int] =
      new Division[Int] {
        override def divide(a1: Int, a2: Int): Option[Int] =
          if (a1 % a2 == 0)
            Some(a1 / a2)
          else
            None
      }
  }
}
