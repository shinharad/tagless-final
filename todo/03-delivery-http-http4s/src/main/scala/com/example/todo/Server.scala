package com.example
package todo

import cats._

import org.http4s._
import scala.concurrent._
import org.http4s.server.blaze.BlazeServerBuilder

trait Server[F[_]] {
  def serve: F[Unit]
}

object Server {
  def dsl[F[_]: effect.ConcurrentEffect: effect.Timer](
      executionContext: ExecutionContext
    )(
      httpApp: HttpApp[F]
    ): F[Server[F]] =
    implicitly[effect.ConcurrentEffect[F]].delay {
      new Server[F] {
        override val serve: F[Unit] =
          BlazeServerBuilder(executionContext)
            .bindHttp()
            .withHttpApp(httpApp)
            .serve
            .compile
            .drain
      }
    }
}
