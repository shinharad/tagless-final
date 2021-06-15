package cats

trait Apply[F[_]] extends Semigroupal[F] with Functor[F]:
  def map2[A, B, R](fa: F[A], fb: F[B])(abr: (A, B) => R): F[R] =
    map(product(fa, fb))(abr.tupled)
