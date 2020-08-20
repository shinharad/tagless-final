package com.example.expressionproblem.initial

object View {
  def apply(exp: Exp): String =
    exp match {
      case Exp.Lit(n)      => s"${n}"
      case Exp.Neg(e)      => s"(-${apply(e)})"
      case Exp.Add(e1, e2) => s"(${apply(e1)} + ${apply(e2)})"
    }

  def prefix(exp: Exp): String =
    exp match {
      case Exp.Lit(n)      => s"${n}"
      case Exp.Neg(e)      => s"(- ${prefix(e)})"
      case Exp.Add(e1, e2) => s"(+ ${prefix(e1)} ${prefix(e2)})"
    }
}
