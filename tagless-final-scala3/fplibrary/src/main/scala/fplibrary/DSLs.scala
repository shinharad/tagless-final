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

// final implicit class FunctorOps[F[_], A](private val fa: F[A]) {
//   def map[B](ab: A => B)(using Functor[F]): F[B] =
//     summon[Functor[F]].map(fa)(ab)
// }

// final implicit class AnyOps[A](private val a: A) extends AnyVal {
//   def pure[F[_]: Applicative]: F[A] =
//     summon[Applicative[F]].pure(a)
// }
