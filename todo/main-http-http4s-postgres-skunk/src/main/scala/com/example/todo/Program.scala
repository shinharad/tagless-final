package com.example
package todo

import java.time.format.DateTimeFormatter

import cats.implicits._

import cats.effect._
import scala.concurrent._

object Program {
  def dsl[F[_]: ContextShift: ConcurrentEffect: Timer: natchez.Trace](
      executionContext: ExecutionContext
    ): F[Unit] =
    SessionPool.dsl.use { resource =>
      for {
        controller <- crud.DependencyGraph.dsl(Pattern, resource)
        server <- Server.dsl(executionContext)(
          HttpApp.dsl(
            controller
          )
        )
        _ <- server.serve
      } yield ()
    }

  private val Pattern =
    DateTimeFormatter.ofPattern("EEEE, MMMM d, yyyy HH:mm")
}
