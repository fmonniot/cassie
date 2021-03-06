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

import com.datastax.driver.core.GettableByNameData


trait ByNameScalarRowDecoder[A] {
  def decode(row: GettableByNameData, name: String): Either[CassieException, A]
}

object ByNameScalarRowDecoder {
  def apply[A](implicit dec: ByNameScalarRowDecoder[A]): ByNameScalarRowDecoder[A] = dec

  def pure[A](func: (GettableByNameData, String) => Either[CassieException, A]): ByNameScalarRowDecoder[A] =
    (row: GettableByNameData, name: String) => func(row, name)

}
