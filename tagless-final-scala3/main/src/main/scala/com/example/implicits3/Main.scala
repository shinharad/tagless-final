package com.example
package implicits3

import fplibrary._

object Main extends App {
  println("─" * 100)

  val dsl: Applicative[Maybe] =
    implicitly[Applicative[Maybe]]

  val maybe: Maybe[Int] =
    dsl.pure(1337)

  println(dsl.map(maybe)(_ + 1))

  println(1337.pure[Maybe].map(_ + 1))
  println(1337.pure[Maybe] map (_ + 1))

  println("─" * 100)
}
