package com.example
package expressionproblem
package `final`

trait Program[A] {
  def run: A
}

object Program {
  object Expression {
    def dsl[A](implicit expression: Expression[A]): Program[A] =
      new Program[A] {
        import expression._

        override val run: A =
          add(
            literal(16),
            negate(
              add(
                literal(1),
                literal(2)
              )
            )
          )
      }
  }

  object Multiplication {
    def dsl[A](
        implicit
        expression: Expression[A],
        multiplication: Multiplication[A]
      ): Program[A] =
      new Program[A] {
        import expression._
        import multiplication._

        override val run: A =
          multiply(
            literal(2),
            Expression.dsl.run
          )
      }
  }

  object MultiplicationInTheMiddle {
    def dsl[A](
        implicit
        expression: Expression[A],
        multiplication: Multiplication[A]
      ): Program[A] =
      new Program[A] {
        import expression._
        import multiplication._

        override val run: A =
          add(
            literal(16),
            negate(
              multiply(
                literal(2),
                add(
                  literal(1),
                  literal(2)
                )
              )
            )
          )
      }
  }
}
