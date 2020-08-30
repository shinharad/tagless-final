package com.example
package todo
package crud

object InMemoryEntityGateway {
  def dsl[F[_]]: EntityGateway[F] =
    new EntityGateway[F] {

      override def writeMany(todos: Vector[Todo]): F[Vector[Todo.Existing]] = ???

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
