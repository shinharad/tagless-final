package cats

import cats.core._

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

  final implicit class TraverseOps[F[_]: Traverse, A](private val fa: F[A]) {
    @inline def traverse[G[_]: Applicative, B](agb: A => G[B]): G[F[B]] =
      F.traverse(fa)(agb)
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

  // implicit val ApplicativeForId: Applicative[Id] =
  //   new Applicative[Id] {
  //     override def map[A, B](fa: Id[A])(ab: A => B): Id[B] =
  //       ab(fa)

  //     override def pure[A](a: A): Id[A] =
  //       a

  //   }

  implicit val TraverseForVector: Traverse[Vector] =
    new Traverse[Vector] {
      override def map[A, B](fa: Vector[A])(ab: A => B): Vector[B] =
        fa.map(ab)
      // traverse[Id, A, B](fa)(ab)

      override def traverse[G[_]: Applicative, A, B](
          fa: Vector[A]
        )(
          agb: A => G[B]
        ): G[Vector[B]] =
        fa.foldRight(Vector.empty[B].pure[G]) {
          (current: A, acc: G[Vector[B]]) =>
            val gbCur: G[B] = agb(current)
            val gbAcc: G[Vector[B]] = acc

            def prepend(b: B, vb: Vector[B]): Vector[B] =
              b +: vb

            ???
        }

    }
}
