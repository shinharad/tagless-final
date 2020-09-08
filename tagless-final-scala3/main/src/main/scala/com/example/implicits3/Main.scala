package com.example
package implicits3

import fplibrary.{ given _, _ }

def (s: String)doStuff: String =
  s + "I'm doing stuff"

object Main extends App {
  println("─" * 100)

  val dsl: Applicative[Maybe] =
    summon[Applicative[Maybe]]

  val maybe: Maybe[Int] =
    dsl.pure(1337)

  println(dsl.map(maybe)(_ + 1))

  println(1337.pure[Maybe].map(_ + 1))
  println(1337.pure[Maybe] map (_ + 1))

  println("─" * 100)

  println(doStuff("What are you doing? "))
  println("What are you doing? ".doStuff)

  println("─" * 100)
}
