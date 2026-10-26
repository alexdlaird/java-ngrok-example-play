lazy val root = (project in file("."))
  .enablePlugins(PlayScala)
  .settings(
    name := """java-ngrok-example-play""",
    organization := "com.github.alexdlaird",
    version := "1.0.0-SNAPSHOT",
    scalaVersion := "2.13.12",
    Compile / packageBin / packageOptions += Package.ManifestAttributes("X-Muppets" -> "42"),
    libraryDependencies ++= Seq(
      guice,
      "org.scalatestplus.play" %% "scalatestplus-play" % "5.1.0" % Test,
      "com.github.alexdlaird" % "java-ngrok" % "4.0.2"
    ),
    scalacOptions ++= Seq(
      "-feature",
      "-deprecation",
      "-Xfatal-warnings"
    )
  )
