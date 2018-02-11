# Cassie

[![Build Status](https://travis-ci.org/fmonniot/cassie.svg?branch=master)](https://travis-ci.org/fmonniot/cassie)
![Project Status](https://img.shields.io/badge/project%20status-experimental-yellowgreen.svg)

When Cassandra meets Cats IO, but cutter.

Cassie is an open-source library which provides a set of tools
to integrate the Datastax Cassandra Java Driver into
the `cats` and `cats-effects` world.

```scala
import cats.effect.IO
import com.datastax.driver.core.Session
import eu.monniot.cassie
import eu.monniot.cassie.implicits._

// Create the driver session as usual
implicit val session: Session = ???

case class MyItem(a: String)

val query: IO[Either[cassie.CassieError, Vector[MyItem]]] = for {
  statement <- cql"SELECT * FROM my_keyspace.my_table"
  result <- cassie.execute(statement)
} yield result.asVectorOf[MyItem]

val query2: IO[Either[cassie.CassieError, Iterable[MyItem]]] = for {
  statement <- cql"SELECT * FROM my_keyspace.my_table"
  result <- cassie.execute(statement)
  more <- cassie.fetchMoreResults(result)
} yield (result ++ more).asIterableOf[MyItem]
```

## Quickstart with sbt

Cassie is published to JCenter and built against scala 2.12.3,
 so you can just add the following to your build:

```scala
resolvers += Resolver.jcenterRepo

libraryDependencies += "eu.monniot.cassie" %% "cassie" % "0.1.0"
```

## Documentation

Cassie documentation will eventually be available at
[francois.monniot.eu/cassie](https://francois.monniot.eu/cassie).

## Contributing

The Cassie project welcomes contributions from anybody wishing to
participate.  All code or documentation that is provided must be
licensed with the same license that Cassie is licensed with (Apache
2.0, see LICENSE.txt).

People are expected to follow the
[Typelevel Code of Conduct](https://typelevel.org/conduct.html) when
discussing Cassie on the Github page or other venues.

Feel free to open an issue if you notice a bug, have an idea for a
feature, or have a question about the code. Pull requests are also
gladly accepted. For more information, check out the
[contributor guide](CONTRIBUTING.md).

## License

All code in this repository is licensed under the Apache License,
Version 2.0.  See [LICENCE.txt](LICENSE.txt).
