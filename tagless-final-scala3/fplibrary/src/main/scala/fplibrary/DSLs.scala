package fplibrary

trait Functor[F[_]] {
  def map[A, B](fa: F[A])(ab: A => B): F[B]
}

trait Applicative[F[_]] extends Functor[F] {
  def pure[A](a: A): F[A]
}

final implicit class FunctorOps[F[_]: Functor, A](private val fa: F[A]) {
  def map[B](ab: A => B): F[B] =
    summon[Functor[F]].map(fa)(ab)
}

final implicit class AnyOps[A](private val a: A) extends AnyVal {
  def pure[F[_]: Applicative]: F[A] =
    summon[Applicative[F]].pure(a)
}
