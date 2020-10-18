package com.example.errorhandling

import cats._

trait Console[F[_]] {
  def good(in: Any): F[Unit]
  def bad(in: Any): F[Unit]
}

object Console {
  def dsl[F[_]: effect.Sync]: Console[F] =
    new Console[F] {
      import scala.Console._

      override def good(in: Any): F[Unit] =
        F.delay(println(GREEN + in + RESET))

      override def bad(in: Any): F[Unit] =
        F.delay(err.println(RED + in + RESET))

    }
}
