package cats

trait Monad[F[_]] extends Applicative[F] with FlatMap[F]:
  extension[A] (fa: F[A])
    def iterateWhile(p: A => Boolean): F[A] =
      fa.flatMap { a =>
        if (p(a))
          iterateWhile(p)
        else
          pure(a)
      }
