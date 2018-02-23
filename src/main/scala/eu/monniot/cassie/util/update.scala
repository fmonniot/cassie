package eu.monniot.cassie.util

import eu.monniot.cassie.CompositeRowDecoder
import eu.monniot.cassie.util.log.LogHandler
import eu.monniot.cassie.util.pos.Pos

object update {


  trait Update[A] {


    def toUpdate0(a: A): Update0 = ???

  }

  object Update {
    /**
     * Construct an `Update` for some composite parameter type `A` with the given SQL string, and
     * optionally a `Pos` and/or `LogHandler` for diagnostics. The normal mechanism
     * for construction is the `sql/fr/fr0` interpolators.
     * @group Constructors
     */
    @SuppressWarnings(Array("org.wartremover.warts.DefaultArguments"))
    def apply[A](sql0: String, pos0: Option[Pos] = None, logHandler0: LogHandler = LogHandler.nop)(
      implicit C: CompositeRowDecoder[A]
    ): Update[A] =
      new Update[A] {
        type I  = A
        val ai  = (a: A) => a
        val ic  = C
        val sql = sql0
        val logHandler = logHandler0
        val pos = pos0
      }
  }


  trait Update0 {

  }

}
