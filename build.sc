// build.sc
import mill._
import mill.scalalib._
import mill.scalalib.publish.{PomSettings, License, Developer, SCM}

object cassie extends Cross[CassieModule]("2.12.4")

class CassieModule(val crossScalaVersion: String) extends CrossSbtModule with PublishModule {
  def artifactName = "cassie"
  def publishVersion = "0.1.0-SNAPSHOT"

  // There is only one module, so we put it at the repository root
  def millSourcePath = ammonite.ops.pwd

  def scalacOptions = Seq(
    "-Ypartial-unification",
    "-deprecation",
    "-feature"
  )

  def ivyDeps = Agg(
    ivy"org.typelevel::cats-core:1.0.1",
    ivy"org.typelevel::cats-effect:0.8",
    ivy"com.datastax.cassandra:cassandra-driver-core:3.4.0",
    ivy"com.chuusai::shapeless:2.3.3"
  )

  object test extends Tests {
    def ivyDeps = Agg(
      ivy"org.scalatest::scalatest:3.0.4"
    )
    def testFramework = "org.scalatest.tools.Framework"
  }

  def pomSettings = PomSettings(
    description = artifactName(),
    organization = "eu.monniot.cassie",
    url = "https://github.com/fmonniot/cassie",
    licenses = Seq(
      License("Apache 2.0", "https://opensource.org/licenses/Apache-2.0")
    ),
    scm = SCM(
      "git://github.com/fmonniot/cassie.git",
      "scm:git://github.com/fmonniot/cassie.git"
    ),
    developers = Seq(
      Developer("fmonniot", "François Monniot","https://francois.monniot.eu")
    )
  )

}
