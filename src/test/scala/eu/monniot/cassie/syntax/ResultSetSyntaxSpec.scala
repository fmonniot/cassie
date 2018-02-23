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

import eu.monniot.cassie.instances.{CompositeRowDecoderInstances, ScalarRowDecoderInstances}
import org.scalatest.{Matchers, WordSpec}

import eu.monniot.cassie.helpers._


//noinspection ZeroIndexToHead
class ResultSetSyntaxSpec extends WordSpec with Matchers
  with ResultSetSyntax with ScalarRowDecoderInstances with CompositeRowDecoderInstances {

  "#asVectorOf" should {

    "convert a ResultSet into a Vector" in {
      val seq = ('a' to 'z').map(_.toString)
      val rs = makeIndexedResultSet(seq)

      val res = rs.asVectorOf[String]

      res shouldEqual Right(seq.toVector)
    }

    "fail when one of the item isn't convertible" in {
      val seq = Seq("b", 1, "c")
      val rs = makeIndexedResultSet(seq)

      val res = rs.asVectorOf[String]

      res shouldBe 'left
    }

    "fail at the first error when multiple" in {
      val seq = Seq("b", 'a', 1, "c")
      val rs = makeIndexedResultSet(seq)

      val res = rs.asVectorOf[String]

      res shouldBe 'left
    }
  }

  "#asAccVectorOf" should {

    "convert a ResultSet into a Vector" in {
      val seq = ('a' to 'z').map(_.toString)
      val rs = makeIndexedResultSet(seq)

      val res = rs.asAccVectorOf[String]

      res shouldEqual Right(seq.toVector)
    }

    "fail when one of the item isn't convertible" in {
      val seq = Seq("b", 1, "c")
      val rs = makeIndexedResultSet(seq)

      val res = rs.asAccVectorOf[String]

      res shouldBe 'left
      res.left.get should have size 1
    }

    "fail with a list of error when multiple" in {
      val seq = Seq("b", 'a', 1, "c")
      val rs = makeIndexedResultSet(seq)

      val res = rs.asAccVectorOf[String]

      res shouldBe 'left
      val nel = res.left.get.toList
      nel should have size 2
    }
  }
}
