package com.example
package todo
package crud

import java.time.format.DateTimeFormatter

import cats._

import org.http4s._

trait Controller[F[_]] {
  def routes: HttpRoutes[F]
}

object Controller {
  def dsl[F[_]: Applicative](
      pattern: DateTimeFormatter,
      boundary: Boundary[F]
    ): Controller[F] =
    new Controller[F] {
      override def routes: HttpRoutes[F] =
        HttpRoutes.empty
    }
}
