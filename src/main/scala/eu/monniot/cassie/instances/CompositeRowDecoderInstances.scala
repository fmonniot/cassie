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

import eu.monniot.cassie.CompositeRowDecoder.pure
import eu.monniot.cassie.{CompositeRowDecoder, ByNameScalarRowDecoder}
import shapeless.{::, HList, HNil, LabelledGeneric, Witness}
import shapeless.labelled.{FieldType, field}

trait CompositeRowDecoderInstances {

  implicit val hNilDecoder: CompositeRowDecoder[HNil] =
    pure(_ => Right(HNil))

  implicit def fieldTypeDecoder[Key <: Symbol, Value](implicit
                                                      witness: Witness.Aux[Key],
                                                      decoder: ByNameScalarRowDecoder[Value]
                                                     ): CompositeRowDecoder[FieldType[Key, Value]] =
    pure(row => decoder.decode(row, witness.value.name).map(field[Key](_)))

  implicit def hListParser[Key <: Symbol, Value, Tail <: HList](implicit
                                                                hDecoder: CompositeRowDecoder[FieldType[Key, Value]],
                                                                tDecoder: CompositeRowDecoder[Tail]
                                                               ): CompositeRowDecoder[FieldType[Key, Value] :: Tail] = {
    pure { row =>
      for {
        h <- hDecoder.decode(row)
        t <- tDecoder.decode(row)
      } yield h :: t
    }
  }

  implicit def genericDecoder[A, R <: HList](implicit
                                             gen: LabelledGeneric.Aux[A, R],
                                             rDecoder: CompositeRowDecoder[R]
                                            ): CompositeRowDecoder[A] =
    pure(row => rDecoder.decode(row).map(gen.from))
}
