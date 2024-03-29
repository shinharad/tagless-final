package com.example
package expressionproblem

import scala.util.chaining.*

import cats.*
import cats.data.*
import cats.instances.all.*

@main def app(): Unit =
  println("-" * 100)

  import `final`.*

  Program
    .Expression
    .dsl[Id, Int](
      using
      Evaluate.Literal.dsl,
      Evaluate.Negation.dsl,
      Evaluate.Addition.dsl
    )
    .run
    .tap(println)

  Program
    .Expression
    .dsl[Id, String](
      using
      View.Literal.dsl,
      View.Negation.dsl,
      View.Addition.dsl
    )
    .run
    .tap(println)

  println("-" * 100)

  Program
    .Multiplication
    .dsl[Id, Int](
      using
      Evaluate.Literal.dsl,
      Evaluate.Negation.dsl,
      Evaluate.Addition.dsl,
      Evaluate.Multiplication.dsl
    )
    .run
    .tap(println)

  Program
    .Multiplication
    .dsl[Id, String](
      using
      View.Literal.dsl,
      View.Negation.dsl,
      View.Addition.dsl,
      View.Multiplication.dsl
    )
    .run
    .tap(println)

  println("-" * 100)

  Program
    .MultiplicationInTheMiddle
    .dsl[Id, Int](
      using
      Evaluate.Literal.dsl,
      Evaluate.Negation.dsl,
      Evaluate.Addition.dsl,
      Evaluate.Multiplication.dsl
    )
    .run
    .tap(println)

  Program
    .MultiplicationInTheMiddle
    .dsl[Id, String](
      using
      View.Literal.dsl,
      View.Negation.dsl,
      View.Addition.dsl,
      View.Multiplication.dsl
    )
    .run
    .tap(println)

  println("-" * 100)

  Program
    .Division
    .dsl[[A] =>> EitherNec[String, A], Int](
      using
      Evaluate.Literal.dsl,
      Evaluate.Negation.dsl,
      Evaluate.Addition.dsl,
      Evaluate.Multiplication.dsl,
      Evaluate.Division.dsl
    )
    .run
    .tap(println)

  Program
    .Division
    .dsl[Id, String](
      using
      View.Literal.dsl,
      View.Negation.dsl,
      View.Addition.dsl,
      View.Multiplication.dsl,
      View.Division.dsl
    )
    .run
    .tap(println)

  println("-" * 100)

  Program
    .DivisionInTheMiddle
    .dsl[[A] =>> EitherNec[String, A], Int](
      using
      Evaluate.Literal.dsl,
      Evaluate.Negation.dsl,
      Evaluate.Addition.dsl,
      Evaluate.Multiplication.dsl,
      Evaluate.Division.dsl
    )
    .run
    .tap(println)

  Program
    .DivisionInTheMiddle
    .dsl[Id, String](
      using
      View.Literal.dsl,
      View.Negation.dsl,
      View.Addition.dsl,
      View.Multiplication.dsl,
      View.Division.dsl
    )
    .run
    .tap(println)

  println("-" * 100)

  Program
    .DivisionWithTwoErrors
    .dsl[[A] =>> EitherNec[String, A], Int](
      using
      Evaluate.Literal.dsl,
      Evaluate.Addition.dsl,
      Evaluate.Division.dsl
    )
    .run
    .tap(println)

  Program
    .DivisionWithTwoErrors
    .dsl[Id, String](
      using
      View.Literal.dsl,
      View.Addition.dsl,
      View.Division.dsl
    )
    .run
    .tap(println)

  println("-" * 100)

  import cats.effect.unsafe.implicits.*

  // format: off
  Program
    .DivisionWithTwoErrors
 // .dsl[Either [           NonEmptyChain[String], *], Int](
 // .dsl[EitherT[Id,        NonEmptyChain[String], *], Int](
    .dsl[[A] =>> EitherT[effect.IO, NonEmptyChain[String], A], Int](
      using
      Evaluate.Literal.dsl,
      Evaluate.Addition.dsl,
      Evaluate.Division.dsl
    )
    .run
    .tap(println)
    .tap(_.value.tap(println))
    .tap(_.value.unsafeRunSync().tap(println))
  // format: on

  Program
    .DivisionWithTwoErrors
    .dsl[Id, String](
      using
      View.Literal.dsl,
      View.Addition.dsl,
      View.Division.dsl
    )
    .run
    .tap(println)

  println("-" * 100)
