package com.example
package waitdontfold

import java.time.LocalDateTime

object Main extends App {
  println("─" * 100)

  println("hello world")

  println("─" * 100)
}

object Scope {
  sealed abstract class Option[+A] extends Product with Serializable {
    import Option._

    // def exists(p: A => Boolean): Boolean =
    //   this match {
    //     case None    => false
    //     case Some(a) => p(a)
    //   }

    def exists(p: A => Boolean): Boolean =
      fold(false)(p)

    // def map[B](f: A => B): Option[B] =
    //   this match {
    //     case None    => None
    //     case Some(a) => Some(f(a))
    //   }

    def map[B](f: A => B): Option[B] =
      fold[Option[B]](None)(a => Some(f(a)))

    // def flatMap[B](f: A => Option[B]): Option[B] =
    //   this match {
    //     case None    => None
    //     case Some(a) => f(a)
    //   }

    def flatMap[B](f: A => Option[B]): Option[B] =
      fold[Option[B]](None)(f)

    final def fold[B](ifNone: => B)(ifSome: A => B): B =
      this match {
        case None    => ifNone
        case Some(a) => ifSome(a)
      }
  }
  object Option {
    final case class Some[+A](a: A) extends Option[A]
    case object None extends Option[Nothing]
  }

  sealed abstract class Todo[+TodoId] extends Product with Serializable {
    import Todo._

    final def fold[B](ifExisting: (TodoId, Data) => B, ifData: (String, LocalDateTime) => B): B =
      this match {
        case Existing(id, data)          => ifExisting(id, data)
        case Data(description, deadline) => ifData(description, deadline)
      }
  }
  object Todo {
    final case class Existing[+TodoId](id: TodoId, data: Data) extends Todo[TodoId]
    final case class Data(description: String, deadline: LocalDateTime) extends Todo[Nothing]
  }

  sealed abstract class Either[+L, +R] extends Product with Serializable {
    import Either._

    final def fold[B](ifLeft: L => B, ifRight: R => B): B =
      this match {
        case Left(l)  => ifLeft(l)
        case Right(r) => ifRight(r)
      }
  }
  object Either {
    final case class Left[+L, +R](l: L) extends Either[L, R]
    final case class Right[+L, +R](r: R) extends Either[L, R]
  }

  sealed abstract class List[+A] extends Product with Serializable {
    import List._

    final def foldLeft[B](seed: => B)(f: (B, A) => B): B =
      this match {
        case Nil =>
          seed

        case head :: tail =>
          val headResult = f(seed, head)
          val result = tail.foldLeft(headResult)(f)

          result
      }

    final def foldRight[B](seed: => B)(f: (A, B) => B): B =
      this match {
        case Nil =>
          seed

        case head :: tail =>
          val tailResult = tail.foldRight(seed)(f)
          val result = f(head, tailResult)

          result
      }

  }
  object List {
    final case class ::[+A](head: A, tail: List[A]) extends List[A]
    case object Nil extends List[Nothing]
  }

  sealed abstract class Tree[+A] extends Product with Serializable {
    import Tree._

    final def foldLeft[B](seed: => B)(f: (B, A) => B): B =
      this match {
        case Leaf =>
          seed

        case Branch(left, element, right) =>
          val currentResult = f(seed, element)
          val rightResult = right.foldLeft(currentResult)(f)
          val leftResult = left.foldLeft(rightResult)(f)

          leftResult
      }

    final def foldRight[B](seed: => B)(f: (A, B) => B): B =
      this match {
        case Leaf =>
          seed

        case Branch(left, element, right) =>
          val leftResult = left.foldRight(seed)(f)
          val rightResult = right.foldRight(leftResult)(f)
          val result = f(element, rightResult)

          result
      }
  }
  object Tree {
    final case class Branch[+A](
        left: Tree[A],
        element: A,
        right: Tree[A]
      ) extends Tree[A]
    case object Leaf extends Tree[Nothing]
  }
}
