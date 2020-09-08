package com.example
package implicits3

import fplibrary._

enum Maybe[+A]:
  case Just(a: A)
  case Nothing

object Maybe:
  given Applicative[Maybe]:
    override def pure[A](a: A): Maybe[A] =
      Maybe.Just(a)

    override def [A, B](fa: Maybe[A])map(ab: A => B): Maybe[B] =
      fa match
        case Just(a) => pure(ab(a))
        case Nothing => Nothing