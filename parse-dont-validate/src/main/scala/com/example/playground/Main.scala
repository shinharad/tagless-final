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

  // Parse, don't validate
  object Custom {
    sealed abstract class List[+A] extends Product with Serializable
    final case class ::[+A](head: A, tail: List[A]) extends List[A]
    case object Nil extends List[Nothing]
  }

  println("─" * 100)

  type Cons[+A] = ::[A]
  val Cons = ::

  def head5[A](nonEmptyList: Cons[A]): A =
    nonEmptyList.head

  // println(head5(List.empty))
  // println(head5(List(1, 2, 3)))

  // val input: List[Int] = List.empty
  val input: List[Int] = List(1, 2, 3)

  // println(head5[Int](input))

  input match {
    case nonEmptyList: Cons[Int] => println(head5(nonEmptyList))
    case _                       => println("can't call head")
  }

  println("─" * 100)

  object Custom2 {
    final case class List[+A](head: Option[A], tail: Option[List[A]] = None)
    val Nil: Custom2.List[Nothing] = List(head = None)
  }

  def head6[A](nonEmptyList: Custom2.List[A]): A =
    nonEmptyList.head.get

  // sealed abstract class Something[-T] extends Product with Serializable

  // object Something {
  //   implicit object anything extends Something[Any]
  //   implicit object contradiction extends Something[Nothing]
  // }

  // def head7[A: Something](nonEmptyList: Custom2.List[A]): A =
  //   nonEmptyList.head.get

  // println(head7(Custom2.Nil))
  // println(head7(Custom2.List(Some(1), None)))
  // println(head7(Custom2.List[Int](Some(1), None)))

  println("─" * 100)
}
