lazy val root = (project in file("."))
  .enablePlugins(PlayScala)
  .settings(
    name := """java-ngrok-example-play""",
    organization := "com.github.alexdlaird",
    version := "1.0.0-SNAPSHOT",
    scalaVersion := "2.13.12",
    libraryDependencies ++= Seq(
      guice,
      "org.scalatestplus.play" %% "scalatestplus-play" % "5.1.0" % Test,
      "com.github.alexdlaird" % "java-ngrok" % "2.2.7"
    ),
    scalacOptions ++= Seq(
      "-feature",
      "-deprecation",
      "-Xfatal-warnings"
    )
  )
