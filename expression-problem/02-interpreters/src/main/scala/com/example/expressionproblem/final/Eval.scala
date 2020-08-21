package com.example.expressionproblem.`final`

object Eval {
  implicit val dsl: Exp[Int] =
    new Exp[Int] {
      override def Lit(n: Int): Int = n
      override def Neg(e: Int): Int = -e
      override def Add(e1: Int, e2: Int): Int = e1 + e2
    }
}
