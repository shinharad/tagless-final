package com.example.expressionproblem.`final`

class Program[Repr](exp: Exp[Repr]) {
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
