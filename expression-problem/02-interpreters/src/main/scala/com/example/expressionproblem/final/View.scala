package com.example
package expressionproblem
package `final`

import cats._
import cats.syntax.all._

object View {
  object Expression {
    def dsl[F[_]: Applicative]: Expression[F, String] =
      new Expression[F, String] {
        override def literal(n: Int): F[String] =
          s"${n}".pure[F]

        override def negate(a: F[String]): F[String] =
          a.map(a => s"(-${a})")

        override def add(a1: F[String], a2: F[String]): F[String] =
          (a1, a2).mapN((a1, a2) => s"(${a1} + ${a2})")

      }
  }

  object Multiplication {
    def dsl[F[_]: Apply]: Multiplication[F, String] =
      new Multiplication[F, String] {
        override def multiply(a1: F[String], a2: F[String]): F[String] =
          (a1, a2).mapN((a1, a2) => s"(${a1} * ${a2})")
      }
  }

  object Division {
    def dsl[F[_]: Apply]: Division[F, String] =
      new Division[F, String] {
        override def divide(a1: F[String], a2: F[String]): F[String] =
          (a1, a2).mapN((a1, a2) => s"(${a1} / ${a2})")
      }
  }
}
