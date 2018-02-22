/*
 * Copyright (c) 2014-2018 by François Monniot.
 * See the project homepage at: https://github.com/fmonniot/cassie
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package eu.monniot

import java.nio.ByteBuffer

import cats.effect.IO
import com.datastax.driver.core._
import com.google.common.util.concurrent.{FutureCallback, Futures, ListenableFuture}
import eu.monniot.cassie.instances.AllInstances
import eu.monniot.cassie.syntax.AllSyntax

import scala.language.implicitConversions


package object cassie {

  type ScalarRowDecoder[A] = ByNameScalarRowDecoder[A] with ByIndexScalarRowDecoder[A]

  object implicits
    extends AllSyntax
    with AllInstances

  // Expose simply the main exception type
  type CassieException = errors.CassieException

  /*
  Some categories:
  - Statement management (CQL, prepared) <- would be interesting to let user pass a case class and use its fields as params
  - Query to result (execute, query, fetch more)
  - Result conversion (ResultSet -> Vector[Row] -> Vector[T])
   */

  def execute(statement: PreparedStatement, args: Any*)
             (implicit session: Session): IO[ResultSet] =
    session.executeAsync(statement.bind(args.map(_.asInstanceOf[Object]): _*)) // TODO Try to remove this cast

  def fetchMoreResults(resultSet: ResultSet)(implicit session: Session): IO[ResultSet] =
    if (resultSet.isFullyFetched) IO.raiseError(new NoSuchElementException("No more results to fetch"))
    else resultSet.fetchMoreResults()


  private[cassie] implicit def listenableFutureToIo[A](lf: ListenableFuture[A]): IO[A] =
    IO.async { cb =>
      Futures.addCallback(lf, new FutureCallback[A] {
        override def onFailure(t: Throwable): Unit = cb(Left(t))

        override def onSuccess(result: A): Unit = cb(Right(result))
      })
    }


  /**
    * A type for the cassandra type `time`, which store a time in nanoseconds
    * @param ts the underlying representation
    */
  final class Time(val ts: Long) extends AnyVal

  /**
    * This let you access any column as a ByteBuffer.
    * Please note that you are responsible for the correct decoding process
    * hence the Unsafe prefix.
    * @param bb the byte buffer containing the data
    */
  final class UnsafeByteBuffer(val bb: ByteBuffer) extends AnyVal

}
