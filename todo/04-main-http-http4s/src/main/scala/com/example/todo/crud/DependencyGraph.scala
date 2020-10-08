package com.example
package todo
package crud

import java.time.format.DateTimeFormatter

import cats._
import cats.implicits._

import cats.effect.concurrent.Ref

object DependencyGraph {
  def dsl[F[_]: effect.Sync](
      pattern: DateTimeFormatter
    ): F[Controller[F]] =
    Ref.of(Vector.empty[Todo.Existing]).map { state =>
      Controller.dsl(
        pattern = pattern,
        boundary = Boundary.dsl[F](
          gateway = InMemoryEntityGateway.dsl(state)
        )
      )
    }
}
