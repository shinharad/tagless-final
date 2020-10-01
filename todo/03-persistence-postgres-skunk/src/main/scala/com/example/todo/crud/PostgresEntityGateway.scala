package com.example
package todo
package crud

import cats._

object PostgresEntityGateway {

  // def dsl[F[_]: effect.Bracket[*[_], Throwable]](
  //     resource: effect.Resource[F, skunk.Session[F]]
  //   ): EntityGateway[F] =

  def dsl[F[_]](
      resource: effect.Resource[F, skunk.Session[F]]
    )(implicit
      B: effect.Bracket[F, Throwable]
    ): EntityGateway[F] =
    new EntityGateway[F] {
      override def writeMany(todos: Vector[Todo]): F[Vector[Todo.Existing]] =
        ???

      override def readManyById(ids: Vector[String]): F[Vector[Todo.Existing]] =
        ???

      override def readManyByPartialDescription(
          partialDescription: String
        ): F[Vector[Todo.Existing]] = ???

      override def readAll: F[Vector[Todo.Existing]] = ???

      override def deleteMany(todos: Vector[Todo.Existing]): F[Unit] = ???

      override def deleteAll: F[Unit] =
        resource.use { session =>
          ???
        }

    }

}
