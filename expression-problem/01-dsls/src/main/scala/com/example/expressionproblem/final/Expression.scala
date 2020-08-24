package com.example
package expressionproblem
package `final`

trait Expression[A] {
  def literal(n: Int): A
  def negate(a: A): A
  def add(a1: A, a2: A): A
}

trait Multiplication[A] {
  def multiply(a1: A, a2: A): A
}

trait Division[A] {
  def divide(a1: A, a2: A): Option[A]
}
