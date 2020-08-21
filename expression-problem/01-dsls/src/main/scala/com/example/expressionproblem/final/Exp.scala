package com.example
package expressionproblem
package `final`

trait Exp[Repr] {
  def Lit(n: Int): Repr
  def Neg(e: Repr): Repr
  def Add(e1: Repr, e2: Repr): Repr
}
