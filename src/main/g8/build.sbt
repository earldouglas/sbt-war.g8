ThisBuild / scalaVersion := "3.6.3"

lazy val scalaJSOptimize: SettingKey[Boolean] =
  settingKey("scalaJSOptimize")

lazy val root =
  crossProject(JSPlatform, JVMPlatform)
    .in(file("."))
    .enablePlugins(SbtWar)
    .settings(
      libraryDependencies += "com.lihaoyi" %%% "upickle" % "4.1.0",
      warResources := warResources.value ++ crossProjectBaseDirectory
        .map(_ / "webapp")
        .map(com.earldouglas.sbt.war.WebappComponents.getResources).value,
    )
    .jvmSettings(
      libraryDependencies += "jakarta.servlet" % "jakarta.servlet-api" % "6.0.0" % Provided,
      libraryDependencies += "org.scalameta" %%% "munit" % "1.0.4" % Test,
    )
    .jsSettings(
      libraryDependencies += "org.scala-js" %%% "scalajs-dom" % "2.8.0",
      {
        lazy val scalaJSWarResources: Def.Initialize[Task[Map[String, File]]] =
          Def.task {

            val jsOutDir: File =
              (Def.taskIf {
                if (scalaJSOptimize.value) {
                  (Compile / fullLinkJS / scalaJSLinkerOutputDirectory).value
                } else {
                  (Compile / fastLinkJS / scalaJSLinkerOutputDirectory).value
                }
              }).value

            val report: Attributed[org.scalajs.linker.interface.Report] =
              (Def.taskIf {
                if (scalaJSOptimize.value) {
                  (Compile / fullLinkJS).value
                } else {
                  (Compile / fastLinkJS).value
                }
              }).value

            val scalaJSWarResources: Map[String, File] =
              report.data.publicModules
                .map(_.jsFileName)
                .flatMap(fn => List(fn, s"\${fn}.map"))
                .map(fn => (fn -> new File(jsOutDir, fn)))
                .toMap

            scalaJSWarResources
          }

        warResources := warResources.value ++ scalaJSWarResources.value,
      },
      scalaJSOptimize := false,
    )
