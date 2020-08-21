package com.example
package expressionproblem
package initial

object Program {
  import Exp._

  // (16 + (-(1 + 2))) = 13
  val exp: Exp = // 13
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