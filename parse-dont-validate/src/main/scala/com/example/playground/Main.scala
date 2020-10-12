package com.example
package playground

import cats._
import cats.data._
import cats.implicits._

object Main extends App {
  println("─" * 100)

  // head: Nothing (throw an Exception)
  def head1[A](list: List[A]): A =
    list.head

  // println(head1(List.empty))
  println(head1(List(1)))

  println("─" * 100)

  // head: Option
  def head2[A](list: List[A]): Option[A] =
    list.headOption

  println(head2(List.empty))
  println(head2(List(1)))

  println("─" * 100)

  // head: Either
  def head3[A](list: List[A]): Either[String, A] =
    scala.util.Random.nextBoolean() match {
      case true =>
        Either.fromOption(list.headOption, "head or empty list")

      case false =>
        Either.catchNonFatal(list.head).leftMap(_.getMessage)
    }

  println(head3(List.empty))
  println(head3(List(1)))

  println("─" * 100)

  // head: F (ApplicativeError)
  def head4[F[_]: ApplicativeError[*[_], String], A](list: List[A]): F[A] =
    scala.util.Random.nextBoolean() match {
      case true =>
        F.fromOption(list.headOption, "head or empty list")

      case false =>
        F.fromEither(Either.catchNonFatal(list.head).leftMap(_.getMessage))

        // Either
        //   .catchNonFatal(list.head)
        //   .leftMap(_.getMessage)
        //   .fold(F.raiseError, F.pure)
    }

  println(head4(List.empty))
  println(head4(List(1)))

  println("─" * 100)

  println(head4[Validated[String, *], Int](List.empty))
  println(head4[Validated[String, *], Int](List(1)))

  println("─" * 100)
}
