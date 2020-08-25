package com.example
package expressionproblem

import scala.util.chaining._

import cats._
import cats.instances.all._

object Main extends App {
  println("-" * 100)

  // initial.Eval.interpret(initial.Program.exp).tap(println)
  // initial.View.interpret(initial.Program.exp).tap(println)
  // initial.View.prefix(initial.Program.exp).tap(println)
  // println("-" * 100)

  import `final`._

  Program
    .Expression
    .dsl(Evaluate.Expression.dsl[Id])
    // .dsl(Evaluate.Expression.dsl[Option])
    // .dsl(Evaluate.Expression.dsl[Either[String, *]])
    .run
    .tap(println)

  // Program
  //   .Expression
  //   .dsl(View.Expression.dsl)
  //   .run
  //   .tap(println)

  // println("-" * 100)

  // Program
  //   .Multiplication
  //   .dsl(Evaluate.Expression.dsl, Evaluate.Multiplication.dsl)
  //   .run
  //   .tap(println)

  // Program
  //   .Multiplication
  //   .dsl(View.Expression.dsl, View.Multiplication.dsl)
  //   .run
  //   .tap(println)

  // println("-" * 100)

  // Program
  //   .MultiplicationInTheMiddle
  //   .dsl(Evaluate.Expression.dsl, Evaluate.Multiplication.dsl)
  //   .run
  //   .tap(println)

  // Program
  //   .MultiplicationInTheMiddle
  //   .dsl(View.Expression.dsl, View.Multiplication.dsl)
  //   .run
  //   .tap(println)

  // println("-" * 100)

  // Program
  //   .Division
  //   .dsl(Evaluate.Expression.dsl, Evaluate.Multiplication.dsl, Evaluate.Division.dsl)
  //   .run
  //   .tap(println)

  // Program
  //   .Division
  //   .dsl(View.Expression.dsl, View.Multiplication.dsl, View.Division.dsl)
  //   .run
  //   .tap(println)

  // println("-" * 100)

  // Program
  //   .DivisionInTheMiddle
  //   .dsl(Evaluate.Expression.dsl, Evaluate.Multiplication.dsl, Evaluate.Division.dsl)
  //   .run
  //   .tap(println)

  // Program
  //   .DivisionInTheMiddle
  //   .dsl(View.Expression.dsl, View.Multiplication.dsl, View.Division.dsl)
  //   .run
  //   .tap(println)

  println("-" * 100)
}
