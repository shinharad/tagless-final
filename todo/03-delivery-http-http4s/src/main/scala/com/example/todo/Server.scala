package com.example
package todo

import cats._

trait Server[F[_]] {
  def serve: F[Unit]
}

object Server {
  def dsl[F[_]: effect.Sync]: F[Server[F]] =
  F.delay {
    new Server[F] {
      override val serve: F[Unit] =
        ???
    }
  }
}
