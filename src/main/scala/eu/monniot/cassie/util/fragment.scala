package eu.monniot.cassie.util



import cats.Monoid
import eu.monniot.cassie.CompositeRowDecoder
import eu.monniot.cassie.instances.composite._
import eu.monniot.cassie.util.log.LogHandler
import eu.monniot.cassie.util.pos.Pos
import eu.monniot.cassie.util.query.{Query, Query0}
import eu.monniot.cassie.util.update.{Update, Update0}
import shapeless.HNil


/**
 * Module defining the `Fragment` data type.
 * This is taken as is from the doobie project.
 */
object fragment {

  /**
   * A statement fragment, which may include interpolated values. Fragments can be composed by
   * concatenation, which maintains the correct offset and mappings for interpolated values. Once
   * constructed a `Fragment` is opaque; it has no externally observable properties. Fragments are
   * eventually used to construct a [[Query0]] or [[Update0]].
   */
  sealed trait Fragment {
    fa =>

    protected type A // type of interpolated argument (existential, HNil for none)
    protected def a: A // the interpolated argument itself
    protected def ca: CompositeRowDecoder[A] // proof that we can map the argument to parameters
    protected def sql: String // snipped of SQL with `ca.length` placeholders

    // Stack frame, used by the query checker to guess the source position. This will go away at
    // some point, possibly in favor of Haoyi's source position doodad.
    protected def pos: Option[Pos]

    /** Concatenate this fragment with another, yielding a larger fragment. */
    def ++(fb: Fragment): Fragment =
      new Fragment {
        type A = (fa.A, fb.A)
        val ca: CompositeRowDecoder[(Fragment.this.A, fb.A)] = {
          implicit val ca: CompositeRowDecoder[Fragment.this.A] = fa.ca
          implicit val cb: CompositeRowDecoder[fb.A] = fb.ca

          CompositeRowDecoder[(fa.A, fb.A)]
        }
        val a = (fa.a, fb.a)
        val sql = fa.sql + fb.sql
        val pos = fa.pos orElse fb.pos
      }

    /** Construct a [[Query0]] from this fragment, with asserted row type `B`. */
    @SuppressWarnings(Array("org.wartremover.warts.DefaultArguments"))
    def query[B: CompositeRowDecoder](implicit h: LogHandler = LogHandler.nop): Query0[B] =
      queryWithLogHandler(h)

    /**
     * Construct a [[Query0]] from this fragment, with asserted row type `B` and the given
     * `LogHandler`.
     */
    def queryWithLogHandler[B](h: LogHandler)(implicit cb: CompositeRowDecoder[B]): Query0[B] =
      Query[A, B](sql, pos, h)(ca, cb).toQuery0(a)

    /** Construct an [[Update0]] from this fragment. */
    @SuppressWarnings(Array("org.wartremover.warts.DefaultArguments"))
    def update(implicit h: LogHandler = LogHandler.nop): Update0 =
      updateWithLogHandler(h)

    /** Construct an [[Update0]] from this fragment with the given `LogHandler`. */
    def updateWithLogHandler(h: LogHandler): Update0 =
      Update[A](sql, pos, h)(ca).toUpdate0(a)

    override def toString =
      s"""Fragment("$sql")"""

    /** Used only for testing; this uses universal equality on the captured argument. */
    @SuppressWarnings(Array("org.wartremover.warts.Equals"))
    private[util] def unsafeEquals(fb: Fragment): Boolean =
      fa.a == fb.a && fa.sql == fb.sql

  }

  object Fragment {

    /**
     * Construct a statement fragment with the given SQL string, which must contain sufficient `?`
     * placeholders to accommodate the fields of the given interpolated value. This is normally
     * accomplished via the string interpolator rather than direct construction.
     */
    @SuppressWarnings(Array("org.wartremover.warts.DefaultArguments"))
    def apply[A0](sql0: String, a0: A0, pos0: Option[Pos] = None)(
      implicit ev: CompositeRowDecoder[A0]
    ): Fragment =
      new Fragment {
        type A = A0
        val ca = ev
        val a = a0
        val sql = sql0
        val pos = pos0
      }

    /**
     * Construct a statement fragment with no interpolated values and no trailing space; the
     * passed SQL string must not contain `?` placeholders.
     */
    @SuppressWarnings(Array("org.wartremover.warts.DefaultArguments"))
    def const0(sql: String, pos: Option[Pos] = None): Fragment =
      Fragment[HNil](sql, HNil, pos)

    /**
     * Construct a statement fragment with no interpolated values and a trailing space; the
     * passed SQL string must not contain `?` placeholders.
     */
    @SuppressWarnings(Array("org.wartremover.warts.DefaultArguments"))
    def const(sql: String, pos: Option[Pos] = None): Fragment =
      const0(sql + " ", pos)

    /** The empty fragment. Adding this to another fragment has no effect. */
    val empty: Fragment =
      const0("")

    /** Statement fragments form a monoid. */
    implicit val FragmentMonoid: Monoid[Fragment] =
      new Monoid[Fragment] {
        val empty = Fragment.empty

        def combine(a: Fragment, b: Fragment) = a ++ b
      }

  }

}
