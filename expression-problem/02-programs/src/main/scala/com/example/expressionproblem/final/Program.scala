package com.example.expressionproblem.`final`

trait Program[Repr] {
  def repr: Repr
}

object Program {
  def dsl[Repr](implicit exp: Exp[Repr]): Program[Repr] =
    new Program[Repr] {
      import exp._

      val repr: Repr =
        Add(
          Lit(16),
          Neg(
            Add(
              Lit(1),
              Lit(2)
            )
          )
        )
    }
}
