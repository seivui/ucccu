name := """paris"""

version := "0.0.1-SNAPSHOT"

scalaVersion := "2.11.1"

resolvers ++= Seq(
  "jBCrypt Repository" at "http://repo1.maven.org/maven2/org/",
  Resolver.sonatypeRepo("snapshots")
)

libraryDependencies ++= Seq(
  javaJdbc,
  javaEbean,
  "org.webjars" % "jquery" % "2.1.1",
  "org.webjars" % "bootstrap" % "3.1.1",
  "org.webjars" % "font-awesome" % "4.1.0",
  "mysql" % "mysql-connector-java" % "5.1.31",
  "org.mindrot" % "jbcrypt" % "0.3m"
)

lazy val root = (project in file(".")).enablePlugins(PlayJava)
