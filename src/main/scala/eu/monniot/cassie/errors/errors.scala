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

/**
  * Module defining the type of exceptions representing unmet expectations.
  * These typically indicate a problem with the schema, with type mapping, with driver compliance, and so on.
  * The intent is that they be as fine-grained as reasonable for diagnostic purposes,
  * but it is not expected that the application should be able to handle them in any meaningful way.
  */
package object errors {

  sealed abstract class CassieException(msg: String, cause: Throwable) extends Exception(msg, cause)

  final case class DecoderError(cause: Throwable)
    extends CassieException("An error occurred while decoding a Cassandra result", cause)

}
