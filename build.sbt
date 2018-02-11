
lazy val core = (project in file(".")).
  settings(
    organization := "eu.monniot.cassie",
    scalaVersion := "2.12.4",
    version := "0.1.0-SNAPSHOT",
    name := "cassie",

    scalacOptions += "-Ypartial-unification",

    libraryDependencies ++= Seq(
      "org.typelevel" %% "cats-effect" % "0.8",
      "org.typelevel" %% "cats-core" % "1.0.1",
      "com.datastax.cassandra" % "cassandra-driver-core" % "3.4.0",
      "com.chuusai" %% "shapeless" % "2.3.3",

      "org.scalatest" %% "scalatest" % "3.0.4" % Test
    )
  )
