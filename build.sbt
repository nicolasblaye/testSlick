import ReleaseTransformations._

name := "TestSlick"

organization in ThisBuild := "com.powerspace.testing"

scalaVersion := "2.12.4"

publishTo := {
  Some(Resolver.file("file",  new File( "/home/nblaye/releases" )) )
}


libraryDependencies ++= Seq(
  "com.typesafe.slick" %% "slick" % "3.2.1",
  "org.postgresql" % "postgresql" % "9.4.1212",
  "com.h2database" % "h2" % "1.4.196" % "test", // used for test only
  "com.typesafe.slick" %% "slick-hikaricp" % "3.2.1",
  "ch.qos.logback" % "logback-core" % "1.2.3",
  "ch.qos.logback" % "logback-classic" % "1.2.3",
  "com.typesafe.scala-logging" %% "scala-logging" % "3.7.2"
)

releaseProcess := Seq[ReleaseStep](
  checkSnapshotDependencies,              // : ReleaseStep
  inquireVersions,                        // : ReleaseStep
  runClean,                               // : ReleaseStep
  runTest,                                // : ReleaseStep
  setReleaseVersion,                      // : ReleaseStep
  commitReleaseVersion,                   // : ReleaseStep, performs the initial git checks
  tagRelease,                             // : ReleaseStep
  publishArtifacts,                       // : ReleaseStep, checks whether `publishTo` is properly set up
  setNextVersion,                         // : ReleaseStep
  commitNextVersion//,                      // : ReleaseStep
//  pushChanges                             // : ReleaseStep, also checks that an upstream branch is properly configured
)