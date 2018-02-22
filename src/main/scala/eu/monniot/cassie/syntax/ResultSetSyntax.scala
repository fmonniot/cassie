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

package eu.monniot.cassie.syntax

import cats.data.NonEmptyList
import cats.syntax.either._
import com.datastax.driver.core.{ResultSet, Row}
import eu.monniot.cassie.{CassieException, CompositeRowDecoder}

import scala.collection.JavaConverters._
import scala.language.implicitConversions


trait ResultSetSyntax {
  implicit final def cassieSyntaxResultSet(rs: ResultSet): ResultSetOps =
    new ResultSetOps(rs)
}


class ResultSetOps(val rs: ResultSet) extends AnyVal {

  /**
    * Will decode each row of the entire `ResultSet` into a Vector.
    * This method is fail-fast, which means it will stop at the first error found.
    *
    * @tparam A The element a single row will be decoded into
    * @return either an error or the vector of items in the `ResultSet`
    */
  def asVectorOf[A: CompositeRowDecoder]: Either[CassieException, Vector[A]] = {
    val decoder = CompositeRowDecoder[A]
    val first: Either[CassieException, Vector[A]] = Right(Vector.empty)

    rs.asScala.foldLeft(first) {
      case (Left(err), _) => Left(err)

      case (Right(acc), row) =>
        decoder.decode(row) match {
          case Right(a) =>

            Right(acc :+ a)

          case Left(err) => Left(err)
        }
    }
  }

  /**
    * Will decode each row of the entire `ResultSet` into a Vector.
    * This method accumulates error, which means it will NOT stop at the first error found but will
    * process the entire `ResultSet` and will returns all errors found (if any).
    *
    * @tparam A The element a single row will be decoded into
    * @return either a list of all errors or the vector of items in the `ResultSet`
    */
  def asAccVectorOf[A: CompositeRowDecoder]: Either[NonEmptyList[CassieException], Vector[A]] = {
    val decoder = CompositeRowDecoder[A]
    val first: Either[NonEmptyList[CassieException], Vector[A]] = Right(Vector.empty)

    rs.asScala.map(decoder.decode).toVector.foldLeft(first) {
      case (Left(nel), Left(err)) => Left(err :: nel)
      case (Right(vector), Right(a)) => Right(vector :+ a)
      case (nel: Left[_, _], Right(_)) => nel
      case (Right(_), Left(err)) => Left(NonEmptyList.one(err))
    }.leftMap(_.reverse)
  }

  def ++(rs2: ResultSet): Iterable[Row] = rs.asScala.view ++ rs2.asScala.view
}