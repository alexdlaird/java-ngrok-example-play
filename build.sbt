lazy val root = (project in file("."))
  .enablePlugins(PlayScala)
  .settings(
    name := """java-ngrok-example-play""",
    organization := "com.github.alexdlaird",
    version := "1.0.0-SNAPSHOT",
    scalaVersion := "2.13.6",
    libraryDependencies ++= Seq(
      guice,
      "org.scalatestplus.play" %% "scalatestplus-play" % "5.0.0" % Test,
      "com.github.alexdlaird" % "java-ngrok" % "1.5.3"
    ),
    scalacOptions ++= Seq(
      "-feature",
      "-deprecation",
      "-Xfatal-warnings"
    )
  )
