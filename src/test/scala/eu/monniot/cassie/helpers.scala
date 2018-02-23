package eu.monniot.cassie

import java.math.BigInteger
import java.net.InetAddress
import java.nio.ByteBuffer
import java.util.{Date, UUID}

import com.datastax.driver.core._
import com.google.common.reflect.TypeToken
import com.google.common.util.concurrent.ListenableFuture

import scala.collection.JavaConverters._


object helpers {

  def makeRow(m: Map[String, Any]): Row = new Row {

    private val v = m.toVector

    private def byName[T](n: String) = m(n).asInstanceOf[T]

    private def byIndex[T](i: Int) = v(i)._2.asInstanceOf[T]

    override def getPartitionKeyToken: Token = ???

    override def getColumnDefinitions: ColumnDefinitions = ???

    override def getToken(i: Int): Token = byIndex(i)

    override def getToken(name: String): Token = byName(name)

    override def getByte(i: Int): Byte = byIndex(i)

    override def getTime(i: Int): Long = byIndex(i)

    override def getTupleValue(i: Int): TupleValue = byIndex(i)

    override def getDouble(i: Int): Double = byIndex(i)

    override def getInet(i: Int): InetAddress = byIndex(i)

    override def getFloat(i: Int): Float = byIndex(i)

    override def getUDTValue(i: Int): UDTValue = byIndex(i)

    override def getBytes(i: Int): ByteBuffer = byIndex(i)

    override def getUUID(i: Int): UUID = byIndex(i)

    override def getBytesUnsafe(i: Int): ByteBuffer = byIndex(i)

    override def getTimestamp(i: Int): Date = byIndex(i)

    override def getList[T](i: Int, elementsClass: Class[T]): java.util.List[T] = ???

    override def getList[T](i: Int, elementsType: TypeToken[T]): java.util.List[T] = ???

    override def get[T](i: Int, targetClass: Class[T]): T = ???

    override def get[T](i: Int, targetType: TypeToken[T]): T = ???

    override def get[T](i: Int, codec: TypeCodec[T]): T = ???

    override def getDate(i: Int): LocalDate = byIndex(i)

    override def getBool(i: Int): Boolean = byIndex(i)

    override def getDecimal(i: Int): java.math.BigDecimal = byIndex(i)

    override def getVarint(i: Int): BigInteger = byIndex(i)

    override def getObject(i: Int): AnyRef = byIndex(i)

    override def getSet[T](i: Int, elementsClass: Class[T]): java.util.Set[T] = ???

    override def getSet[T](i: Int, elementsType: TypeToken[T]): java.util.Set[T] = ???

    override def getShort(i: Int): Short = byIndex(i)

    override def getString(i: Int): String = byIndex(i)

    override def getMap[K, V](i: Int, keysClass: Class[K], valuesClass: Class[V]): java.util.Map[K, V] = ???

    override def getMap[K, V](i: Int, keysType: TypeToken[K], valuesType: TypeToken[V]): java.util.Map[K, V] = ???

    override def getLong(i: Int): Long = byIndex(i)

    override def getInt(i: Int): Int = byIndex(i)

    override def isNull(i: Int): Boolean = !v.isDefinedAt(i)

    override def getByte(name: String): Byte = byName(name)

    override def getTime(name: String): Long = byName(name)

    override def getTupleValue(name: String): TupleValue = byName(name)

    override def getDouble(name: String): Double = byName(name)

    override def getInet(name: String): InetAddress = byName(name)

    override def getFloat(name: String): Float = byName(name)

    override def getUDTValue(name: String): UDTValue = byName(name)

    override def getBytes(name: String): ByteBuffer = byName(name)

    override def getUUID(name: String): UUID = byName(name)

    override def getBytesUnsafe(name: String): ByteBuffer = byName(name)

    override def getTimestamp(name: String): Date = byName(name)

    override def getList[T](name: String, elementsClass: Class[T]): java.util.List[T] = ???

    override def getList[T](name: String, elementsType: TypeToken[T]): java.util.List[T] = ???

    override def get[T](name: String, targetClass: Class[T]): T = ???

    override def get[T](name: String, targetType: TypeToken[T]): T = ???

    override def get[T](name: String, codec: TypeCodec[T]): T = ???

    override def getDate(name: String): LocalDate = byName(name)

    override def getBool(name: String): Boolean = byName(name)

    override def getDecimal(name: String): java.math.BigDecimal = byName(name)

    override def getVarint(name: String): BigInteger = byName(name)

    override def getObject(name: String): AnyRef = byName(name)

    override def getSet[T](name: String, elementsClass: Class[T]): java.util.Set[T] = ???

    override def getSet[T](name: String, elementsType: TypeToken[T]): java.util.Set[T] = ???

    override def getShort(name: String): Short = byName(name)

    override def getString(name: String): String = byName(name)

    override def getMap[K, V](name: String, keysClass: Class[K], valuesClass: Class[V]): java.util.Map[K, V] = ???

    override def getMap[K, V](name: String, keysType: TypeToken[K], valuesType: TypeToken[V]): java.util.Map[K, V] = ???

    override def getLong(name: String): Long = byName(name)

    override def getInt(name: String): Int = byName(name)

    override def isNull(name: String): Boolean = m.get(name).isEmpty
  }

  def makeIndexedResultSet(l: Seq[Any]): ResultSet =
    makeResultSet(l.map(e => Map("" -> e)))

  def makeResultSet(l: Seq[Map[String, Any]]): ResultSet = new ResultSet {
    override def one(): Row = ???

    override def getColumnDefinitions: ColumnDefinitions = ???

    override def wasApplied(): Boolean = ???

    override def isExhausted: Boolean = ???

    override def all(): java.util.List[Row] = ???

    override def getExecutionInfo: ExecutionInfo = ???

    override def getAvailableWithoutFetching: Int = ???

    override def isFullyFetched: Boolean = ???

    override def iterator(): java.util.Iterator[Row] = l.iterator.map(makeRow).asJava

    override def getAllExecutionInfo: java.util.List[ExecutionInfo] = ???

    override def fetchMoreResults(): ListenableFuture[ResultSet] = ???
  }

}
