package com.example
package todo

import scala.util.chaining._

import cats._
import cats.data._
import cats.implicits._

import org.http4s._
import org.http4s.implicits._
import org.http4s.server.Router

object HttpApp {
  def dsl[F[_]: effect.Concurrent](
      routes: NonEmptyChain[HttpRoutes[F]]
    ): HttpApp[F] =
    routes
      .reduceLeft(_ <+> _)
      .pipe(routes => Router("api" -> routes))
      .orNotFound
}
