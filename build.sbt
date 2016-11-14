name := "hotels-akka-rest"

organization := "Qbyte Consulting"

version := "1.0"

scalaVersion := "2.11.8"

resolvers += Classpaths.typesafeReleases

libraryDependencies ++= {
  val akkaV     = "2.4.12"
  val akkaHttpV = "2.4.11"
  Seq(
    "com.typesafe.akka" %% "akka-slf4j"                         % akkaV,
    "com.typesafe.akka" %% "akka-http-core"                     % akkaHttpV,
    "com.typesafe.akka" %% "akka-http-experimental"             % akkaHttpV,
    "com.typesafe.akka" %% "akka-http-spray-json-experimental"  % akkaHttpV,
    "ch.qos.logback"    %  "logback-classic"                    % "1.1.7"
  )
}

lazy val root = project.in(file(".")).enablePlugins(JavaServerAppPackaging)

mainClass in Global := Some("com.qbyte.hotelapi.WebServer")
