package fplibrary

trait Functor[F[_]]:
  extension [A](fa: F[A])
    def map[B](ab: A => B): F[B]

trait Applicative[F[_]] extends Functor[F]:
  def pure[A](a: A): F[A]

extension [F[_]: Applicative, A](a: A)
  def pure: F[A] = summon[Applicative[F]].pure(a)
