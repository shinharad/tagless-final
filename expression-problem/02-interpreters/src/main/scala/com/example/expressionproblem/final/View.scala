package com.example
package expressionproblem
package `final`

object View {
  val dsl: Exp[String] =
    new Exp[String] {
      override def Lit(n: Int): String = s"${n}"
      override def Neg(e: String): String = s"(-${e})"
      override def Add(e1: String, e2: String): String = s"(${e1} + ${e2})"
    }
}
