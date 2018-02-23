package eu.monniot.cassie.util

import java.util.logging.Logger

import scala.concurrent.duration.FiniteDuration

/** A module of types and instances for logged statements. */
object log {

  /**
   * Algebraic type of events that can be passed to a `LogHandler`, both parameterized by the
   * argument type of the SQL input parameters (this is typically an `HList`).
   * @group Events
   */
  sealed abstract class LogEvent extends Product with Serializable {

    /** The complete SQL string as seen by JDBC. */
    def sql: String

    /** The query arguments. */
    def args: List[Any]

  }

  /** @group Events */ final case class Success          (sql: String, args: List[Any], exec: FiniteDuration, processing: FiniteDuration                    ) extends LogEvent
  /** @group Events */ final case class ProcessingFailure(sql: String, args: List[Any], exec: FiniteDuration, processing: FiniteDuration, failure: Throwable) extends LogEvent
  /** @group Events */ final case class ExecFailure      (sql: String, args: List[Any], exec: FiniteDuration,                             failure: Throwable) extends LogEvent

  /**
   * A sink for `LogEvent`s.
   * @group Handlers
   */
  final case class LogHandler(unsafeRun: LogEvent => Unit)

  /**
   * Module of instances and constructors for `LogHandler`.
   * @group Handlers
   */
  object LogHandler {

    /**
     * A do-nothing `LogHandler`.
     * @group Constructors
     */
    val nop: LogHandler =
      LogHandler(_ => ())

    /**
     * A LogHandler that writes a default format to a JDK Logger. This is provided for demonstration
     * purposes and is not intended for production use.
     * @group Constructors
     */
    val jdkLogHandler: LogHandler = {
      val jdkLogger = Logger.getLogger(getClass.getName)
      LogHandler {

        case Success(s, a, e1, e2) =>
          jdkLogger.info(s"""Successful Statement Execution:
                            |
            |  ${s.lines.dropWhile(_.trim.isEmpty).mkString("\n  ")}
                            |
            | arguments = [${a.mkString(", ")}]
                            |   elapsed = ${e1.toMillis} ms exec + ${e2.toMillis} ms processing (${(e1 + e2).toMillis} ms total)
          """.stripMargin)

        case ProcessingFailure(s, a, e1, e2, t) =>
          jdkLogger.severe(s"""Failed Resultset Processing:
                              |
            |  ${s.lines.dropWhile(_.trim.isEmpty).mkString("\n  ")}
                              |
            | arguments = [${a.mkString(", ")}]
                              |   elapsed = ${e1.toMillis} ms exec + ${e2.toMillis} ms processing (failed) (${(e1 + e2).toMillis} ms total)
                              |   failure = ${t.getMessage}
          """.stripMargin)

        case ExecFailure(s, a, e1, t) =>
          jdkLogger.severe(s"""Failed Statement Execution:
                              |
            |  ${s.lines.dropWhile(_.trim.isEmpty).mkString("\n  ")}
                              |
            | arguments = [${a.mkString(", ")}]
                              |   elapsed = ${e1.toMillis} ms exec (failed)
                              |   failure = ${t.getMessage}
          """.stripMargin)

      }
    }


  }

}