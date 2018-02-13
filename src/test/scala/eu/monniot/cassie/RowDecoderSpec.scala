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

package eu.monniot.cassie

import java.nio.ByteBuffer

import org.scalatest.{Matchers, WordSpec}


class RowDecoderSpec extends WordSpec with Matchers {

  "ByNameScalarRowDecoder" should {

    import instances.scalar._

    "exists for primitives types" in {
      ByNameScalarRowDecoder[Boolean]
      ByNameScalarRowDecoder[Byte]
      ByNameScalarRowDecoder[Short]
      ByNameScalarRowDecoder[Int]
      ByNameScalarRowDecoder[Long]
      ByNameScalarRowDecoder[Float]
      ByNameScalarRowDecoder[Double]
      ByNameScalarRowDecoder[String]
      ByNameScalarRowDecoder[BigInt]
      ByNameScalarRowDecoder[BigDecimal]
    }

    "exists for other cassandra primitive types" in {
      ByNameScalarRowDecoder[java.util.Date]
      ByNameScalarRowDecoder[com.datastax.driver.core.LocalDate]
      ByNameScalarRowDecoder[java.time.LocalDate]
      ByNameScalarRowDecoder[Time]
      ByNameScalarRowDecoder[UnsafeByteBuffer]
      ByNameScalarRowDecoder[ByteBuffer]
      ByNameScalarRowDecoder[java.util.UUID]
      ByNameScalarRowDecoder[java.net.InetAddress]
      ByNameScalarRowDecoder[com.datastax.driver.core.UDTValue]
    }

    "exists for container types" in {
      ByNameScalarRowDecoder[Option[String]]
      ByNameScalarRowDecoder[List[String]]
      ByNameScalarRowDecoder[Set[String]]
      ByNameScalarRowDecoder[Map[String, String]]
      ByNameScalarRowDecoder[(String, String)]
    }
  }

  "ByIndexScalarRowDecoder" should {

    import instances.scalar._

    "exists for primitives types" in {
      ByIndexScalarRowDecoder[Boolean]
      ByIndexScalarRowDecoder[Byte]
      ByIndexScalarRowDecoder[Short]
      ByIndexScalarRowDecoder[Int]
      ByIndexScalarRowDecoder[Long]
      ByIndexScalarRowDecoder[Float]
      ByIndexScalarRowDecoder[Double]
      ByIndexScalarRowDecoder[String]
      ByIndexScalarRowDecoder[BigInt]
      ByIndexScalarRowDecoder[BigDecimal]
    }

    "exists for other cassandra primitive types" in {
      ByIndexScalarRowDecoder[java.util.Date]
      ByIndexScalarRowDecoder[com.datastax.driver.core.LocalDate]
      ByIndexScalarRowDecoder[java.time.LocalDate]
      ByIndexScalarRowDecoder[Time]
      ByIndexScalarRowDecoder[UnsafeByteBuffer]
      ByIndexScalarRowDecoder[ByteBuffer]
      ByIndexScalarRowDecoder[java.util.UUID]
      ByIndexScalarRowDecoder[java.net.InetAddress]
      ByIndexScalarRowDecoder[com.datastax.driver.core.UDTValue]
    }

    "exists for container types" in {
      ByIndexScalarRowDecoder[Option[String]]
      ByIndexScalarRowDecoder[List[String]]
      ByIndexScalarRowDecoder[Set[String]]
      ByIndexScalarRowDecoder[Map[String, String]]
      ByIndexScalarRowDecoder[(String, String)]
    }
  }

  "CompositeRowDecoder" should {

    import RowDecoderSpec._
    import instances.scalar._
    import instances.composite._

    "be derived for unary products" in {
      CompositeRowDecoder[X]
      CompositeRowDecoder[Y]
      CompositeRowDecoder[P]
      CompositeRowDecoder[Q]
    }

    "be derived for generic products" in {
      CompositeRowDecoder[Z]
      CompositeRowDecoder[S.type]
    }
  }

}

object RowDecoderSpec {

  final case class X(x: Int)

  final case class Y(x: String) extends AnyVal

  final case class P(x: Int) extends AnyVal

  final case class Q(x: String)

  final case class Z(i: Int, s: String)

  object S

  final case class Reg1(x: Int)

  final case class Reg2(x: Int)

}
