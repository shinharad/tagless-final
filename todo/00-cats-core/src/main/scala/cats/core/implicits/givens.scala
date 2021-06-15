package cats.implicits

import cats.*

given Eq[String] = new Eq[String]:
  def eqv(x: String, y: String): Boolean =
    x == y

extension[A] (a: A)
  def pure[F[_]: Applicative]: F[A] =
    summon[Applicative[F]].pure(a)

given Traverse[Vector] =
  new Traverse[Vector]:
    override def traverse[G[_]: Applicative, A, B](
        fa: Vector[A]
      )(
        agb: A => G[B]
      ): G[Vector[B]] =
      fa.foldRight(Vector.empty[B].pure[G]) { (current, acc) =>
        summon[Applicative[G]].map2(agb(current), acc)(_ +: _)
      }

    extension[A] (fa: Vector[A])
      override def map[B](ab: A => B): Vector[B] = fa.map(ab)

given Traverse[List] =
  new Traverse[List]:

    override def traverse[G[_]: Applicative, A, B](
        fa: List[A]
      )(
        agb: A => G[B]
      ): G[List[B]] =
      fa.foldRight(List.empty[B].pure[G]) { (current, acc) =>
        summon[Applicative[G]].map2(agb(current), acc)(_ +: _)
      }

    extension[A] (fa: List[A])
      override def map[B](ab: A => B): List[B] = fa.map(ab)

given Traverse[Set] =
  new Traverse[Set]:
    override def traverse[G[_]: Applicative, A, B](
        fa: Set[A]
      )(
        agb: A => G[B]
      ): G[Set[B]] =
      fa.foldLeft(Set.empty[B].pure[G]) { (acc, current) =>
        summon[Applicative[G]].map2(acc, agb(current))(_ + _)
      }

    extension[A] (fa: Set[A])
      override def map[B](ab: A => B): Set[B] = fa.map(ab)
  
extension[F[_]: Traverse, G[_]: Applicative, A](fga: F[G[A]])
  def sequence: G[F[A]] =
    summon[Traverse[F]].sequence(fga)

extension[F[_]: Traverse, A](fa: F[A])
  def traverse[G[_]: Applicative, B](agb: A => G[B]): G[F[B]] =
    summon[Traverse[F]].traverse(fa)(agb)
