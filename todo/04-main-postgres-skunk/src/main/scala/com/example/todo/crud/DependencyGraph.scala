package com.example
package todo
package crud

import java.time.format.DateTimeFormatter

import cats._
import cats.implicits._

object DependencyGraph {
  def dsl[F[_]: effect.Sync](
      pattern: DateTimeFormatter,
      console: Console[F],
      random: Random[F],
      resource: effect.Resource[F, skunk.Session[F]]
    ): F[Controller[F]] =
    PostgresEntityGateway.dsl(resource).map { gateway =>
      Controller.dsl(
        pattern = pattern,
        boundary = Boundary.dsl[F](gateway),
        console = FancyConsole.dsl(console),
        random = random
      )
    }
}
