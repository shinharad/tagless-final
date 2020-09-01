package com.example
package todo
package crud

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

import scala.util.Try

trait Controller[F[_]] {
  def run(): F[Unit]
}

object Controller {
  def dsl[F[_]: FancyConsole: Random](
      boundary: Boundary[F],
      pattern: DateTimeFormatter
    ): Controller[F] =
    new Controller[F] {
      override val run: F[Unit] = ???
    }
}
