/*
 * Copyright (c) 2014-2018 by François Monniot.
 * See the project homepage at: https://francois.monniot.eu/cassie
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

package example

import cats.effect.IO
import com.datastax.driver.core.Session
import eu.monniot.cassie
import eu.monniot.cassie.implicits._
import org.scalatest.{FlatSpec, Matchers}

class ExampleSpec extends FlatSpec with Matchers {

  "The Cassie library" should "have an example" in {

    case class MyItem(a: String)

    implicit val session: Session = ???

    val testingQuery: IO[Either[cassie.CassieError, Vector[MyItem]]] = for {
      statement <- cql"SELECT * FROM my_keyspace.my_table"
      result <- cassie.execute(statement)
      //      more <- fetchMoreResults(result)
    } yield result.asVectorOf[MyItem]

    val testingQuery2: IO[Either[cassie.CassieError, Iterable[MyItem]]] = for {
      statement <- cql"SELECT * FROM my_keyspace.my_table"
      result <- cassie.execute(statement)
      more <- cassie.fetchMoreResults(result)
    } yield (result ++ more).asIterableOf[MyItem]
  }

}
