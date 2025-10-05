scalaVersion := "3.7.3"

libraryDependencies += "jakarta.servlet" % "jakarta.servlet-api" % "6.0.0" % Provided
libraryDependencies += "org.scalameta" %% "munit" % "1.1.1" % Test

enablePlugins(SbtWar)
