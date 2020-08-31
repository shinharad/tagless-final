package cats

trait Semigroupal[F[_]] {
  def product[A, B](fa: F[A], fb: F[B]): F[(A, B)]
}

trait Apply[F[_]] extends Semigroupal[F] with Functor[F] {
  def map2[A, B, Result](fa: F[A], fb: F[B])(abr: (A, B) => Result): F[Result] =
    map(product(fa, fb))(abr.tupled)
}

trait Applicative[F[_]] extends Apply[F] {
  def pure[A](a: A): F[A]
}
