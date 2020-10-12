package com.example
package todo
package crud

import java.util.UUID

import cats._
import cats.implicits._

object PostgresEntityGateway {
  def dsl[F[_]: effect.Sync](
      resource: effect.Resource[F, skunk.Session[F]]
    ): F[EntityGateway[F, UUID]] =
    F.delay {
      new EntityGateway[F, UUID] {
        override def writeMany(
            todos: Vector[Todo[UUID]]
          ): F[Vector[Todo.Existing[UUID]]] =
          todos.traverse {
            case insert: Todo.Data =>
              resource.use { session =>
                session
                  .prepare(Statement.Insert.one)
                  .use { preparedQuery =>
                    preparedQuery.unique(insert)
                  }
              }

            case update: Todo.Existing[UUID] =>
              resource.use { session =>
                session
                  .prepare(Statement.Update.one)
                  .use { preparedQuery =>
                    preparedQuery.unique(update)
                  }
              }
          }

        override def readManyById(
            ids: Vector[UUID]
          ): F[Vector[Todo.Existing[UUID]]] =
          resource.use { session =>
            session
              .prepare(Statement.Select.many(ids.size))
              .use { preparedQuery =>
                preparedQuery
                  .stream(ids.to(List), ChunkSizeInBytes)
                  .compile
                  .toVector
              }
          }

        override def readManyByPartialDescription(
            partialDescription: String
          ): F[Vector[Todo.Existing[UUID]]] =
          resource.use { session =>
            session
              .prepare(Statement.Select.byDescription)
              .use { preparedQuery =>
                preparedQuery
                  .stream(partialDescription, ChunkSizeInBytes)
                  .compile
                  .toVector
              }
          }

        override val readAll: F[Vector[Todo.Existing[UUID]]] =
          resource.use { session =>
            session
              .execute(Statement.Select.all)
              .map(_.to(Vector))
          }

        override def deleteMany(todos: Vector[Todo.Existing[UUID]]): F[Unit] =
          resource.use { session =>
            session
              .prepare(Statement.Delete.many(todos.size))
              .use { preparedCommand =>
                preparedCommand
                  .execute(todos.to(List).map(_.id))
                  .void
              }
          }

        override val deleteAll: F[Unit] =
          resource.use { session =>
            session
              .execute(Statement.Delete.all)
              .void
          }
      }
    }

  private val ChunkSizeInBytes: Int =
    1024
}
