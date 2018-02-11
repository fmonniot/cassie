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

package eu.monniot.cassie

import com.datastax.driver.core.Row


trait CompositeRowDecoder[A] {
  def decode(row: Row): Either[CassieError, A]
}

object CompositeRowDecoder {

  def apply[A](implicit dec: CompositeRowDecoder[A]): CompositeRowDecoder[A] = dec

  def pure[A](func: (Row) => Either[CassieError, A]): CompositeRowDecoder[A] = (row: Row) => func(row)
}