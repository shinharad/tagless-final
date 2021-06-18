package com.example
package todo
package crud

import java.time.format.DateTimeFormatter

import cats._
import cats.implicits._

import cats.effect.Ref

object DependencyGraph {
  def dsl[F[_]: effect.Sync](
      pattern: DateTimeFormatter,
      console: Console[F],
      random: Random[F]
    ): F[Controller[F]] =
    Ref[F].of(Vector.empty[Todo.Existing[Int]]).map { state =>
      Controller.dsl(
        pattern = pattern,
        boundary = Boundary.dsl(
          gateway = InMemoryEntityGateway.dsl(state)
        ),
        console = FancyConsole.dsl(console),
        random = random
      )
    }
}
