package com.example
package todo
package crud

import cats._
import cats.implicits._

object PostgresEntityGateway {

  // def dsl[F[_]: effect.Bracket[*[_], Throwable]](
  //     resource: effect.Resource[F, skunk.Session[F]]
  //   ): EntityGateway[F] =

  // def dsl[F[_]](
  //     resource: effect.Resource[F, skunk.Session[F]]
  //   )(implicit
  //     C: fs2.Stream.Compiler[F, F],
  //     B: effect.Bracket[F, Throwable]
  //   ): EntityGateway[F] =

  def dsl[F[_]: effect.Sync: UUIDGenerator](
      resource: effect.Resource[F, skunk.Session[F]]
    ): F[EntityGateway[F]] =
    F.delay {
      new EntityGateway[F] {
        override def writeMany(todos: Vector[Todo]): F[Vector[Todo.Existing]] =
          todos.traverse {
            case insert: Todo.Data =>
              resource.use { session =>
                session
                  .prepare(Statement.Insert.one)
                  .use { preparedQuery =>
                    preparedQuery.unique(insert)
                  }
              }

            // F.genUUID
            //   .map(id => Todo.Existing(id.toString, insert))
            //   .flatMap { e =>
            //     resource.use { session =>
            //       session
            //         .prepare(Statement.Insert.WithUUID.one)
            //         .use { preparedCommand =>
            //           preparedCommand
            //             .execute(e)
            //             .as(e)
            //         }
            //     }
            //   }

            case update: Todo.Existing =>
              resource.use { session =>
                session
                  .prepare(Statement.Update.one)
                  .use { preparedQuery =>
                    preparedQuery.unique(update)
                  }
              }
          }

        override def readManyById(
            ids: Vector[String]
          ): F[Vector[Todo.Existing]] =
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
          ): F[Vector[Todo.Existing]] =
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

  private val ChunkSizeInBytes: Int =
    1024

}
