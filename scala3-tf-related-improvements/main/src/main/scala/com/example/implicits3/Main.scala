package com.example
package implicits3

import fplibrary.{ given, * }

@main def main(): Unit =
  println("─" * 100)

  trait DSL1[F[_]]
  trait DSL2[F[_]]

  given DSL1[Maybe] with {}
  given DSL2[Maybe] with {}

  def program[F[_]: DSL1: DSL2: Applicative](a: Int, b: String): F[(Int, String)] =
    (a, b).pure

  println(program[Maybe](1337, "hello world"))
  println(program(1337, "hello world")(summon[DSL1[Maybe]], summon[DSL2[Maybe]], summon[Applicative[Maybe]]))
  // println(program[Maybe](1337, "hello world")(summon, summon, summon)) // NG
  println(program[Maybe](1337, "hello world")(implicitly, implicitly, implicitly))

  println("─" * 100)

  final case class DBConfig(url: String, password: String)

  def program2[F[_]: DSL1: DSL2: Applicative](a: Int, b: String)(using c: DBConfig)(using d: String): F[(Int, String, DBConfig, String)] =
    (a, b, c, d).pure

  given DBConfig = 
    DBConfig("some url", "super secret password")

  given String = "hi"

  println(program2[Maybe](1337, "hello world"))
  println(program2(1337, "hello world")(summon[DSL1[Maybe]], summon[DSL2[Maybe]], summon[Applicative[Maybe]], summon[String]))
  // println(program2[Maybe](1337, "hello world")(summon, summon, summon, summon)) // => compile error
  println(program2[Maybe](1337, "hello world")(implicitly, implicitly, implicitly, implicitly))

  println("─" * 100)

  def program3[F[_]: DSL1: DSL2](a: Int, b: String)(using Applicative[F]): F[(Int, String)] =
    (a, b).pure

  def program4[F[_]](a: Int, b: String)(using DSL1[F], DSL2[F], Applicative[F]): F[(Int, String)] =
    (a, b).pure

  println(program3[Maybe](1337, "hello world"))
  println(program3(1337, "hello world")(summon[DSL1[Maybe]], summon[DSL2[Maybe]], summon[Applicative[Maybe]]))

  println(program4[Maybe](1337, "hello world"))
  // println(program4(1337, "hello world")(summon[DSL1[Maybe]], summon[DSL2[Maybe]], summon[Applicative[Maybe]]))
  // println(program4(1337, "hello world")(implicitly[DSL1[Maybe]], implicitly[DSL2[Maybe]], implicitly[Applicative[Maybe]]))

  println("─" * 100)

  // Context Functions
  def program5[F[_]](a: Int, b: String): (DSL1[F], DSL2[F], Applicative[F]) ?=> F[(Int, String)] =
    (a, b).pure

  type Common[F[_], A] = (DSL1[F], DSL2[F], Applicative[F]) ?=> F[A]

  def program6[F[_]](a: Int, b: String): Common[F, (Int, String)] =
    (a, b).pure

  def program7[F[_]](a: Int, b: String)(using Applicative[F]): Common[F, (Int, String)] =
    (a, b).pure

  println(program5[Maybe](1337, "hello world"))
  println(program6[Maybe](1337, "hello world"))
  println(program7[Maybe](1337, "hello world"))

  println("─" * 100)
