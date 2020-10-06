package com.example
package todo

import java.time.format.DateTimeFormatter

import cats._
import cats.data._
import cats.implicits._

import cats.effect._

object Program {
  def dsl[F[_]: Concurrent: ContextShift: natchez.Trace]: F[Unit] =
    SessionPool.dsl.use { resource =>
      for {
        controller <- crud.DependencyGraph.dsl(Pattern, resource)
        httpApp = HttpApp.dsl(
          NonEmptyChain(
            controller.routes
          )
        )
        server <- Server.dsl
        _ <- server.serve
      } yield ()
    }

  private val Pattern =
    DateTimeFormatter.ofPattern("EEEE, MMMM d, yyyy HH:mm")
}
