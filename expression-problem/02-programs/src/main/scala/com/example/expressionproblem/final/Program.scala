package com.example
package expressionproblem
package `final`

trait Program[F[_], A] {
  def run: F[A]
}

object Program {
  object Expression {
    def dsl[F[_], A](implicit expression: Expression[F, A]): Program[F, A] =
      new Program[F, A] {
        import expression._

        override val run: F[A] =
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
    def dsl[F[_], A](
        implicit
        expression: Expression[F, A],
        multiplication: Multiplication[F, A]
      ): Program[F, A] =
      new Program[F, A] {
        import expression._
        import multiplication._

        override val run: F[A] =
          multiply(
            literal(2),
            Expression.dsl.run
          )
      }
  }

  object MultiplicationInTheMiddle {
    def dsl[F[_], A](
        implicit
        expression: Expression[F, A],
        multiplication: Multiplication[F, A]
      ): Program[F, A] =
      new Program[F, A] {
        import expression._
        import multiplication._

        override val run: F[A] =
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

  object Division {
    def dsl[F[_], A](
        implicit
        expression: Expression[F, A],
        multiplication: Multiplication[F, A],
        division: Division[F, A]
      ): Program[F, A] =
      new Program[F, A] {
        import expression._
        import division._

        override val run: F[A] =
          divide(
            Multiplication.dsl.run,
            literal(2)
          )
      }
  }

  object DivisionInTheMiddle {
    def dsl[F[_], A](
        implicit
        expression: Expression[F, A],
        multiplication: Multiplication[F, A],
        division: Division[F, A]
      ): Program[F, A] =
      new Program[F, A] {
        import expression._
        import multiplication._
        import division._

        override val run: F[A] =
          add(
            literal(16),
            negate(
              divide(
                multiply(
                  literal(2),
                  add(
                    literal(1),
                    literal(2)
                  )
                ),
                literal(2)
              )
            )
          )
      }
  }

}
