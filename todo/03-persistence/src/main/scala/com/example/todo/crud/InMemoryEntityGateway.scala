package com.example
package todo
package crud

import cats._
import cats.implicits._

object InMemoryEntityGateway {
  def dsl[F[_]: effect.Sync]: EntityGateway[F] =
    new EntityGateway[F] {
      var state: Vector[Todo.Existing] = Vector.empty
      val nextId: F[Int] =
        F.delay(state.size)

      override def writeMany(todos: Vector[Todo]): F[Vector[Todo.Existing]] =
        todos.traverse(writeOne)

      private def writeOne(todo: Todo): F[Todo.Existing] =
        todo match {
          case item: Todo.Data     => createOne(item)
          case item: Todo.Existing => updateOne(item)
        }

      private def createOne(todo: Todo.Data): F[Todo.Existing] =
        nextId
          .map(id => Todo.Existing(id.toString, todo))
          .flatMap { created =>
            F.delay(state :+= created).as(created)
          }

      private def updateOne(todo: Todo.Existing): F[Todo.Existing] =
        F.delay {
            state = state.filterNot(_.id === todo.id) :+ todo
          }
          .as(todo)

      override def readManyById(ids: Vector[String]): F[Vector[Todo.Existing]] =
        F.delay(state.filter(todo => ids.contains(todo.id)))

      override def readManyByPartialDescription(
          partialDescription: String
        ): F[Vector[Todo.Existing]] =
        F.delay(
          state.filter(
            _.description
              .toLowerCase
              .contains(partialDescription.toLowerCase)
          )
        )

      override def readAll: F[Vector[Todo.Existing]] =
        F.delay(state)

      override def deleteMany(todos: Vector[Todo.Existing]): F[Unit] =
        F.delay {
          state = state.filterNot(todo => todos.map(_.id).contains(todo.id))
        }

      override def deleteAll: F[Unit] =
        F.delay {
          state = Vector.empty
        }

    }
}
