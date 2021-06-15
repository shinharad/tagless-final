package cats

trait Eq[A]:
  def eqv(x: A, y: A): Boolean
  def neqv(x: A, y: A): Boolean =
    !eqv(x, y)

  extension (x: A)(using ev: Eq[A])
    def ===(y: A): Boolean =
      ev.eqv(x, y)

    def =!=(y: A): Boolean =
      ev.neqv(x, y)
