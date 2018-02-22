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

package eu.monniot.cassie.instances

import com.datastax.driver.core.{GettableByIndexData, GettableByNameData}
import eu.monniot.cassie.errors.CassieException
import eu.monniot.cassie.{ByIndexScalarRowDecoder, ByNameScalarRowDecoder, ScalarRowDecoder, Time, UnsafeByteBuffer}

import scala.collection.JavaConverters._
import scala.reflect.ClassTag


trait ScalarRowDecoderInstances
  extends BooleanInstance
    with ByteInstance
    with ShortInstance
    with IntInstance
    with LongInstance
    with FloatInstance
    with DoubleInstance
    with StringInstance
    with BigIntInstance
    with BigDecimalInstance
    with JavaDataInstance
    with CassLocalDateInstance
    with JavaLocalDateInstance
    with TimeInstance
    with UnsafeByteBufferInstance
    with ByteBufferInstance
    with UUIDInstance
    with INetAddressInstance
    with UDTValueInstance
    with OptionInstance
    with ListInstance
    with SetInstance
    with MapInstance
    with TupleInstance

/*
 * Primitives types
 */

trait BooleanInstance {
  implicit val booleanRowDecoder: ScalarRowDecoder[Boolean] =
    new ByNameScalarRowDecoder[Boolean] with ByIndexScalarRowDecoder[Boolean] {
      override def decode(row: GettableByNameData, name: String): Either[CassieException, Boolean] =
        trying(Right(row.getBool(name)))

      override def decode(row: GettableByIndexData, index: Int): Either[CassieException, Boolean] =
        trying(Right(row.getBool(index)))
    }
}

trait ByteInstance {
  implicit val byteRowDecoder: ScalarRowDecoder[Byte] =
    new ByNameScalarRowDecoder[Byte] with ByIndexScalarRowDecoder[Byte] {
      override def decode(row: GettableByNameData, name: String): Either[CassieException, Byte] =
        trying(Right(row.getByte(name)))

      override def decode(row: GettableByIndexData, index: Int): Either[CassieException, Byte] =
        trying(Right(row.getByte(index)))
    }
}

trait ShortInstance {
  implicit val shortRowDecoder: ScalarRowDecoder[Short] =
    new ByNameScalarRowDecoder[Short] with ByIndexScalarRowDecoder[Short] {
      override def decode(row: GettableByNameData, name: String): Either[CassieException, Short] =
        trying(Right(row.getShort(name)))

      override def decode(row: GettableByIndexData, index: Int): Either[CassieException, Short] =
        trying(Right(row.getShort(index)))
    }
}

trait IntInstance {
  implicit val intRowDecoder: ScalarRowDecoder[Int] =
    new ByNameScalarRowDecoder[Int] with ByIndexScalarRowDecoder[Int] {
      override def decode(row: GettableByNameData, name: String): Either[CassieException, Int] =
        trying(Right(row.getInt(name)))

      override def decode(row: GettableByIndexData, index: Int): Either[CassieException, Int] =
        trying(Right(row.getInt(index)))
    }
}

trait LongInstance {
  implicit val longRowDecoder: ScalarRowDecoder[Long] =
    new ByNameScalarRowDecoder[Long] with ByIndexScalarRowDecoder[Long] {
      override def decode(row: GettableByNameData, name: String): Either[CassieException, Long] =
        trying(Right(row.getLong(name)))

      override def decode(row: GettableByIndexData, index: Int): Either[CassieException, Long] =
        trying(Right(row.getLong(index)))
    }
}

trait FloatInstance {
  implicit val floatRowDecoder: ScalarRowDecoder[Float] =
    new ByNameScalarRowDecoder[Float] with ByIndexScalarRowDecoder[Float] {
      override def decode(row: GettableByNameData, name: String): Either[CassieException, Float] =
        trying(Right(row.getFloat(name)))

      override def decode(row: GettableByIndexData, index: Int): Either[CassieException, Float] =
        trying(Right(row.getFloat(index)))
    }
}

trait DoubleInstance {
  implicit val doubleRowDecoder: ScalarRowDecoder[Double] =
    new ByNameScalarRowDecoder[Double] with ByIndexScalarRowDecoder[Double] {
      override def decode(row: GettableByNameData, name: String): Either[CassieException, Double] =
        trying(Right(row.getDouble(name)))

      override def decode(row: GettableByIndexData, index: Int): Either[CassieException, Double] =
        trying(Right(row.getDouble(index)))
    }
}

trait StringInstance {
  implicit val stringRowDecoder: ScalarRowDecoder[String] =
    new ByNameScalarRowDecoder[String] with ByIndexScalarRowDecoder[String] {
      override def decode(row: GettableByNameData, name: String): Either[CassieException, String] =
        trying(Right(row.getString(name)))

      override def decode(row: GettableByIndexData, index: Int): Either[CassieException, String] =
        trying(Right(row.getString(index)))
    }
}

trait BigIntInstance {
  implicit val bigIntRowDecoder: ScalarRowDecoder[BigInt] =
    new ByNameScalarRowDecoder[BigInt] with ByIndexScalarRowDecoder[BigInt] {
      override def decode(row: GettableByNameData, name: String): Either[CassieException, BigInt] =
        trying(Right(row.getVarint(name)))

      override def decode(row: GettableByIndexData, index: Int): Either[CassieException, BigInt] =
        trying(Right(row.getVarint(index)))
    }
}

trait BigDecimalInstance {
  implicit val bigDecimalRowDecoder: ScalarRowDecoder[BigDecimal] =
    new ByNameScalarRowDecoder[BigDecimal] with ByIndexScalarRowDecoder[BigDecimal] {
      override def decode(row: GettableByNameData, name: String): Either[CassieException, BigDecimal] =
        trying(Right(row.getDecimal(name)))

      override def decode(row: GettableByIndexData, index: Int): Either[CassieException, BigDecimal] =
        trying(Right(row.getDecimal(index)))
    }
}

/*
 * CQL Primitives types
 */

trait JavaDataInstance {
  implicit val javaDateRowDecoder: ScalarRowDecoder[java.util.Date] =
    new ByNameScalarRowDecoder[java.util.Date] with ByIndexScalarRowDecoder[java.util.Date] {
      override def decode(row: GettableByNameData, name: String): Either[CassieException, java.util.Date] =
        trying(Right(row.getTimestamp(name)))

      override def decode(row: GettableByIndexData, index: Int): Either[CassieException, java.util.Date] =
        trying(Right(row.getTimestamp(index)))
    }
}

trait CassLocalDateInstance {
  implicit val cassLocalRowDecoder: ScalarRowDecoder[com.datastax.driver.core.LocalDate] =
    new ByNameScalarRowDecoder[com.datastax.driver.core.LocalDate] with ByIndexScalarRowDecoder[com.datastax.driver.core.LocalDate] {
      override def decode(row: GettableByNameData, name: String): Either[CassieException, com.datastax.driver.core.LocalDate] =
        trying(Right(row.getDate(name)))

      override def decode(row: GettableByIndexData, index: Int): Either[CassieException, com.datastax.driver.core.LocalDate] =
        trying(Right(row.getDate(index)))
    }
}

trait JavaLocalDateInstance {
  implicit val javaLocalDateRowDecoder: ScalarRowDecoder[java.time.LocalDate] =
    new ByNameScalarRowDecoder[java.time.LocalDate] with ByIndexScalarRowDecoder[java.time.LocalDate] {
      override def decode(row: GettableByNameData, name: String): Either[CassieException, java.time.LocalDate] = trying {
        val date = row.getDate(name)
        Right(java.time.LocalDate.of(date.getYear, date.getMonth, date.getDay))
      }

      override def decode(row: GettableByIndexData, index: Int): Either[CassieException, java.time.LocalDate] = trying {
        val date = row.getDate(index)
        Right(java.time.LocalDate.of(date.getYear, date.getMonth, date.getDay))
      }
    }
}

trait TimeInstance {
  implicit val timeRowDecoder: ScalarRowDecoder[Time] =
    new ByNameScalarRowDecoder[Time] with ByIndexScalarRowDecoder[Time] {
      override def decode(row: GettableByNameData, name: String): Either[CassieException, Time] =
        trying(Right(new Time(row.getTime(name))))

      override def decode(row: GettableByIndexData, index: Int): Either[CassieException, Time] =
        trying(Right(new Time(row.getTime(index))))
    }
}

trait UnsafeByteBufferInstance {
  implicit val unsafeByteBufferRowDecoder: ScalarRowDecoder[UnsafeByteBuffer] =
    new ByNameScalarRowDecoder[UnsafeByteBuffer] with ByIndexScalarRowDecoder[UnsafeByteBuffer] {
      override def decode(row: GettableByNameData, name: String): Either[CassieException, UnsafeByteBuffer] =
        trying(Right(new UnsafeByteBuffer(row.getBytesUnsafe(name))))

      override def decode(row: GettableByIndexData, index: Int): Either[CassieException, UnsafeByteBuffer] =
        trying(Right(new UnsafeByteBuffer(row.getBytesUnsafe(index))))
    }
}

trait ByteBufferInstance {
  implicit val byteBufferRowDecoder: ScalarRowDecoder[java.nio.ByteBuffer] =
    new ByNameScalarRowDecoder[java.nio.ByteBuffer] with ByIndexScalarRowDecoder[java.nio.ByteBuffer] {
      override def decode(row: GettableByNameData, name: String): Either[CassieException, java.nio.ByteBuffer] =
        trying(Right(row.getBytes(name)))

      override def decode(row: GettableByIndexData, index: Int): Either[CassieException, java.nio.ByteBuffer] =
        trying(Right(row.getBytes(index)))
    }
}

trait UUIDInstance {
  implicit val uuidRowDecoder: ScalarRowDecoder[java.util.UUID] =
    new ByNameScalarRowDecoder[java.util.UUID] with ByIndexScalarRowDecoder[java.util.UUID] {
      override def decode(row: GettableByNameData, name: String): Either[CassieException, java.util.UUID] =
        trying(Right(row.getUUID(name)))

      override def decode(row: GettableByIndexData, index: Int): Either[CassieException, java.util.UUID] =
        trying(Right(row.getUUID(index)))
    }
}

trait INetAddressInstance {
  implicit val iNetAddressRowDecoder: ScalarRowDecoder[java.net.InetAddress] =
    new ByNameScalarRowDecoder[java.net.InetAddress] with ByIndexScalarRowDecoder[java.net.InetAddress] {
      override def decode(row: GettableByNameData, name: String): Either[CassieException, java.net.InetAddress] =
        trying(Right(row.getInet(name)))

      override def decode(row: GettableByIndexData, index: Int): Either[CassieException, java.net.InetAddress] =
        trying(Right(row.getInet(index)))
    }
}

trait UDTValueInstance {
  implicit val udtValueRowDecoder: ScalarRowDecoder[com.datastax.driver.core.UDTValue] =
    new ByNameScalarRowDecoder[com.datastax.driver.core.UDTValue] with ByIndexScalarRowDecoder[com.datastax.driver.core.UDTValue] {
      override def decode(row: GettableByNameData, name: String): Either[CassieException, com.datastax.driver.core.UDTValue] =
        trying(Right(row.getUDTValue(name)))

      override def decode(row: GettableByIndexData, index: Int): Either[CassieException, com.datastax.driver.core.UDTValue] =
        trying(Right(row.getUDTValue(index)))
    }
}

/*
 * Container types
 */

trait OptionInstance {
  implicit def optionByNameRowDecoder[A: ByNameScalarRowDecoder]: ByNameScalarRowDecoder[Option[A]] =
    ByNameScalarRowDecoder.pure((row, name) => trying {
      if (row.isNull(name)) Right(None) else ByNameScalarRowDecoder[A].decode(row, name).map(Option(_))
    })

  implicit def optionByIndexRowDecoder[A: ByIndexScalarRowDecoder]: ByIndexScalarRowDecoder[Option[A]] =
    ByIndexScalarRowDecoder.pure((row, index) => trying {
      if (row.isNull(index)) Right(None) else ByIndexScalarRowDecoder[A].decode(row, index).map(Option(_))
    })
}

trait ListInstance {
  implicit def listByNameRowDecoder[A](implicit aClass: ClassTag[A]): ByNameScalarRowDecoder[List[A]] =
    ByNameScalarRowDecoder.pure((row, name) => trying {
      Right(row.getList(name, aClass.runtimeClass.asInstanceOf[Class[A]]).asScala.toList)
    })

  implicit def listByIndexRowDecoder[A](implicit aClass: ClassTag[A]): ByIndexScalarRowDecoder[List[A]] =
    ByIndexScalarRowDecoder.pure((row, index) => trying {
      Right(row.getList(index, aClass.runtimeClass.asInstanceOf[Class[A]]).asScala.toList)
    })
}

trait SetInstance {
  implicit def setByNameRowDecoder[A](implicit aClass: ClassTag[A]): ByNameScalarRowDecoder[Set[A]] =
    ByNameScalarRowDecoder.pure((row, name) => trying {
      Right(row.getSet(name, aClass.runtimeClass.asInstanceOf[Class[A]]).asScala.toSet)
    })

  implicit def setByIndexRowDecoder[A](implicit aClass: ClassTag[A]): ByIndexScalarRowDecoder[Set[A]] =
    ByIndexScalarRowDecoder.pure((row, index) => trying {
      Right(row.getSet(index, aClass.runtimeClass.asInstanceOf[Class[A]]).asScala.toSet)
    })
}

trait MapInstance {
  implicit def mapByNameRowDecoder[K, V](implicit kClass: ClassTag[K], vClass: ClassTag[V]): ByNameScalarRowDecoder[Map[K, V]] =
    ByNameScalarRowDecoder.pure((row, name) => trying {
      Right(row.getMap(name,
        kClass.runtimeClass.asInstanceOf[Class[K]],
        vClass.runtimeClass.asInstanceOf[Class[V]]
      ).asScala.toMap)
    })

  implicit def mapByIndexRowDecoder[K, V](implicit kClass: ClassTag[K], vClass: ClassTag[V]): ByIndexScalarRowDecoder[Map[K, V]] =
    ByIndexScalarRowDecoder.pure((row, index) => trying {
      Right(row.getMap(index,
        kClass.runtimeClass.asInstanceOf[Class[K]],
        vClass.runtimeClass.asInstanceOf[Class[V]]
      ).asScala.toMap)
    })
}

// TODO Support for tuple up to 22
trait TupleInstance {

  implicit def tuple2ByNameDecoder[A: ByIndexScalarRowDecoder, B: ByIndexScalarRowDecoder]: ByNameScalarRowDecoder[(A, B)] =
    ByNameScalarRowDecoder.pure((row, name) => {
      for {
        tuple <- trying(Right(row.getTupleValue(name)))
        a <- ByIndexScalarRowDecoder[A].decode(tuple, 0)
        b <- ByIndexScalarRowDecoder[B].decode(tuple, 1)
      } yield (a, b)
    })

  implicit def tuple2ByIndexDecoder[A: ByIndexScalarRowDecoder, B: ByIndexScalarRowDecoder]: ByIndexScalarRowDecoder[(A, B)] =
    ByIndexScalarRowDecoder.pure((row, index) => {
      for {
        tuple <- trying(Right(row.getTupleValue(index)))
        a <- ByIndexScalarRowDecoder[A].decode(tuple, 0)
        b <- ByIndexScalarRowDecoder[B].decode(tuple, 1)
      } yield (a, b)
    })
}
