package com.example.expressionproblem.initial

object Eval {
  def apply(exp: Exp): Int =
    exp match {
      case Exp.Lit(n)      => n
      case Exp.Neg(e)      => -apply(e)
      case Exp.Add(e1, e2) => apply(e1) + apply(e2)
    }
}
