package com.example
package todo
package crud

import cats._
import cats.implicits._

object InMemoryEntityGateway {
  def dsl[F[_]: effect.Sync]: EntityGateway[F] =
    new EntityGateway[F] {
      var nextId: Int = 0
      var state: Vector[Todo.Existing] = Vector.empty

      override def writeMany(todos: Vector[Todo]): F[Vector[Todo.Existing]] =
        ???

      private def createOne(todo: Todo.Data): F[Todo.Existing] =
        F.delay {
          val created =
            Todo.Existing(
              id = nextId.toString,
              data = todo
            )

          state :+= created

          nextId += 1

          created
        }

      private def updateOne(todo: Todo.Existing): F[Todo.Existing] =
        F.delay {
          state = state.filterNot(_.id === todo.id) :+ todo

          todo
        }

      override def readManyById(ids: Vector[String]): F[Vector[Todo.Existing]] =
        ???

      override def readManyByPartialDescription(
          partialDescription: String
        ): F[Vector[Todo.Existing]] = ???

      override def readAll: F[Vector[Todo.Existing]] = ???

      override def deleteMany(todos: Vector[Todo.Existing]): F[Unit] = ???

      override def deleteAll: F[Unit] = ???

    }
}
