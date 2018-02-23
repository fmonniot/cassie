package eu.monniot.cassie.util

import eu.monniot.cassie.CompositeRowDecoder
import eu.monniot.cassie.util.log.LogHandler
import eu.monniot.cassie.util.pos.Pos

object query {


  trait Query[A, B] {


    def toQuery0(a: A): Query0[B] = ???

  }

  object Query {

    /**
     * Construct a `Query` with the given SQL string, an optional `Pos` for diagnostic
     * purposes, and composite type arguments for input and output types. Note that the most common
     * way to construct a `Query` is via the `sql` interpolator.
     * @group Constructors
     */
    @SuppressWarnings(Array("org.wartremover.warts.DefaultArguments"))
    def apply[A, B](sql0: String, pos0: Option[Pos] = None,
                    logHandler0: LogHandler = LogHandler.nop)(implicit A: CompositeRowDecoder[A], B: CompositeRowDecoder[B]): Query[A, B] =
      new Query[A, B] {
        type I = A
        type O = B
        val ai: A => I = a => a
        val ob: O => B = o => o
        implicit val ic: CompositeRowDecoder[I] = A
        implicit val oc: CompositeRowDecoder[O] = B
        val sql = sql0
        val pos = pos0
        val logHandler = logHandler0
      }

    /*
    /** @group Typeclass Instances */
    implicit val queryProfunctor: Profunctor[Query] =
      new Profunctor[Query] {
        def dimap[A, B, C, D](fab: Query[A,B])(f: C => A)(g: B => D): Query[C,D] =
          fab.contramap(f).map(g)
      }

    /** @group Typeclass Instances */
    implicit def queryCovariant[A]: Functor[Query[A, ?]] =
      new Functor[Query[A, ?]] {
        def map[B, C](fa: Query[A, B])(f: B => C): Query[A, C] =
          fa.map(f)
      }

    /** @group Typeclass Instances */
    implicit def queryContravariant[B]: Contravariant[Query[?, B]] =
      new Contravariant[Query[?, B]] {
        def contramap[A, C](fa: Query[A, B])(f: C => A): Query[C, B] =
          fa.contramap(f)
      }
*/
  }

  trait Query0[A] {

  }

}
