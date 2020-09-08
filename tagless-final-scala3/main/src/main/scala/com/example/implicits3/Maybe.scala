package com.example
package implicits3

import fplibrary._

sealed abstract class Maybe[+A] extends Product with Serializable
object Maybe {
  final case class Just[+A](a: A) extends Maybe[A]
  case object Nothing extends Maybe[scala.Nothing]

  implicit val dsl: Applicative[Maybe] =
    new Applicative[Maybe] {
      override def pure[A](a: A): Maybe[A] =
        Maybe.Just(a)

      override def [A, B](fa: Maybe[A])map(ab: A => B): Maybe[B] =
        fa match {
          case Just(a) => pure(ab(a))
          case Nothing => Nothing
        }
    }

}
