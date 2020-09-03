package com.example
package todo
package crud

import java.time.format.DateTimeFormatter

import cats._

import cats.effect.concurrent.Ref

object DependencyGraph {
  def dsl[F[_]: effect.Sync](
      pattern: DateTimeFormatter,
      console: Console[F],
      random: Random[F]
    ): Controller[F] =
    Controller.dsl(
      pattern = pattern,
      boundary = Boundary.dsl[F](
        gateway = InMemoryEntityGateway.dsl(Ref.of(Vector.empty))
      ),
      console = FancyConsole.dsl(console),
      random = random
    )
}
