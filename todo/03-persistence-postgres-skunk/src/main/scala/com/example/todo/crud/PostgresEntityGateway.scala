package com.example
package todo
package crud

import cats._
import cats.syntax.all._

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
        resource.use { session =>
          session
            .prepare(Statement.Select.many(ids.size))
            .use { preparedQuery =>
              preparedQuery
                .stream(ids.to(List), 1024)
                .compile
                .toVector
            }
        }

      override def readManyByPartialDescription(
          partialDescription: String
        ): F[Vector[Todo.Existing]] = ???

      override def readAll: F[Vector[Todo.Existing]] =
        resource.use { session =>
          session
            .execute(Statement.Select.all)
            .map(_.to(Vector))
        }

      override def deleteMany(todos: Vector[Todo.Existing]): F[Unit] =
        resource.use { session =>
          session
            .prepare(Statement.Delete.many(todos.size))
            .use { preparedCommand =>
              preparedCommand
                .execute(todos.to(List).map(_.id))
                .void
            }
        }

      override def deleteAll: F[Unit] =
        resource.use { session =>
          session
            .execute(Statement.Delete.all)
            .void
        }

    }

}
