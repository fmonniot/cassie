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


import eu.monniot.cassie.errors.DecoderError

import scala.util.Try

package object instances {

  object all extends AllInstances

  object scalar extends ScalarRowDecoderInstances

  object composite extends CompositeRowDecoderInstances


  // Some utilities functions used by the instances

  private[instances] def trying[A](block: => Either[CassieException, A]): Either[CassieException, A] = {
    Try(block).fold(
      throwable => Left(DecoderError(throwable)),
      identity
    )
  }

}
