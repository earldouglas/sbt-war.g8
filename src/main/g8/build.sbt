scalaVersion := "3.1.0"
libraryDependencies += "jakarta.servlet" % "jakarta.servlet-api" % "6.0.0" % Provided
libraryDependencies += "com.novocode" % "junit-interface" % "0.11" % "test"
enablePlugins(SbtWar)
