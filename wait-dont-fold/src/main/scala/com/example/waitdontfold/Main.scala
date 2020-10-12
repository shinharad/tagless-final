package com.example
package waitdontfold

import java.time.LocalDateTime

object Main extends App {
  println("─" * 100)

  println("hello world")

  println("─" * 100)
}

object Scope {
  sealed abstract class Option[+A] extends Product with Serializable
  object Option {
    final case class Some[+A](a: A) extends Option[A]
    case object None extends Option[Nothing]
  }

  sealed abstract class Todo[+TodoId] extends Product with Serializable
  object Todo {
    final case class Existing[+TodoId](id: TodoId, data: Data) extends Todo[TodoId]
    final case class Data(description: String, deadline: LocalDateTime) extends Todo[Nothing]
  }

  sealed abstract class Either[+L, +R] extends Product with Serializable
  object Either {
    final case class Left[+L, +R](l: L) extends Either[L, R]
    final case class Some[+L, +R](r: R) extends Either[L, R]
  }

  sealed abstract class List[+A] extends Product with Serializable
  object List {
    final case class ::[+A](head: A, tail: List[A]) extends List[A]
    case object Nil extends List[Nothing]
  }

  sealed abstract class Tree[+A] extends Product with Serializable
  object Tree {
    final case class Branch[+A](
        left: Tree[A],
        element: A,
        right: Tree[A]
      ) extends Tree[A]
    case object Leaf extends Tree[Nothing]
  }
}
