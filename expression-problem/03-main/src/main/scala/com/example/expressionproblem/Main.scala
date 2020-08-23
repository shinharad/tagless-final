package com.example
package expressionproblem

object Main extends App {
  println("-" * 100)

  println(initial.Eval.interpret(initial.Program.exp))
  println(initial.View.interpret(initial.Program.exp))
  println(initial.View.prefix(initial.Program.exp))
  println("-" * 100)

  import `final`._

  println(Program.dsl(Evaluate.dsl).run)
  println(Program.dsl(View.dsl).run)

  println("-" * 100)
}