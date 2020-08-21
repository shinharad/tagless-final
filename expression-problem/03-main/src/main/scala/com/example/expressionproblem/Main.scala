package com.example.expressionproblem

object Main extends App {
  println("-" * 100)

  // println(initial.Eval(initial.Program.exp))
  // println(initial.View(initial.Program.exp))
  // println(initial.View.prefix(initial.Program.exp))
  // println("-" * 100)

  import `final`._
  import Eval._
  // import View._

  // println(Program.dsl(Eval.dsl).repr)
  println(Program.dsl[Int].repr)
  println(Program.dsl(View.dsl).repr)

  println("-" * 100)
}