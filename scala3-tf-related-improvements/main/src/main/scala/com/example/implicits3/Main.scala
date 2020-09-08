package com.example
package implicits3

import fplibrary.{ given _, _ }

object Main extends App:
  println("─" * 100)

  trait DSL1[F[_]]
  trait DSL2[F[_]]

  given [F[_]] as DSL1[F]
  given [F[_]] as DSL2[F]

  def program[F[_]: DSL1: DSL2: Applicative](a: Int, b: String): F[(Int, String)] =
    (a, b).pure

  println(program[Maybe](1337, "hello world"))
  println(program(1337, "hello world")(summon[DSL1[Maybe]], summon[DSL2[Maybe]], summon[Applicative[Maybe]]))
  // println(program[Maybe](1337, "hello world")(summon, summon, summon))
  println(program[Maybe](1337, "hello world")(implicitly, implicitly, implicitly))

  println("─" * 100)

  final case class DBConfig(url: String, password: String)

  def program2[F[_]: DSL1: DSL2: Applicative](a: Int, b: String)(using c: DBConfig)(using d: String): F[(Int, String, DBConfig, String)] =
    (a, b, c, d).pure
  // def program2[F[_]: DSL1: DSL2: Applicative](a: Int, b: String)(using c: DBConfig): F[(Int, String, DBConfig)] =
  //   (a, b, c).pure

  given as DBConfig =
    DBConfig("some url", "super secret password")

  given as String = "hi"

  println(program2[Maybe](1337, "hello world"))
  // println(program2(1337, "hello world")(summon[DSL1[Maybe]], summon[DSL2[Maybe]], summon[Applicative[Maybe]], summon[DBConfig]))
  // println(program2[Maybe](1337, "hello world")(summon, summon, summon, summon)) // => compile error
  // println(program2[Maybe](1337, "hello world")(implicitly, implicitly, implicitly, implicitly))

  println("─" * 100)

  def program3[F[_]: DSL1: DSL2](a: Int, b: String)(using Applicative[F]): F[(Int, String)] =
    (a, b).pure

  def program4[F[_]](a: Int, b: String)(using DSL1[F], DSL2[F], Applicative[F]): F[(Int, String)] =
    (a, b).pure

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
