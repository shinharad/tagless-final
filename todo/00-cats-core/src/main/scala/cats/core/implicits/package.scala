package cats

package object implicits {
  final implicit class FunctorOps[F[_]: Functor, A](
      private val fa: F[A]
    ) {
    @inline def map[B](ab: A => B): F[B] =
      F.map(fa)(ab)

    @inline def as[B](b: B): F[B] =
      F.map(fa)(_ => b)

    @inline def void: F[Unit] =
      F.map(fa)(_ => ())
  }

  final implicit class AnyOps[A](private val a: A) {
    @inline def pure[F[_]: Applicative]: F[A] =
      F.pure(a)
  }

  final implicit class EqOps[A: Eq](private val x: A) {
    @inline def ===(y: A): Boolean =
      A.eqv(x, y)

    @inline def =!=(y: A): Boolean =
      A.neqv(x, y)
  }

  implicit val EqForString: Eq[String] =
    new Eq[String] {
      def eqv(x: String, y: String): Boolean =
        x == y
    }
}
