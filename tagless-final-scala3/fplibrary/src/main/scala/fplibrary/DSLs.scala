package fplibrary

trait Functor[F[_]] {
  def map[A, B](fa: F[A])(ab: A => B): F[B]
}

trait Applicative[F[_]] extends Functor[F] {
  def pure[A](a: A): F[A]
}