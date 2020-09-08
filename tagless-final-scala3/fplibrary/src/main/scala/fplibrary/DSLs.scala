package fplibrary

trait Functor[F[_]] {
  def [A, B](fa: F[A])map(ab: A => B): F[B]
}

trait Applicative[F[_]] extends Functor[F] {
  def pure[A](a: A): F[A]
}

given [A] as AnyRef {
  def [F[_]: Applicative](a: A).pure: F[A] =
    summon[Applicative[F]].pure(a)
}
