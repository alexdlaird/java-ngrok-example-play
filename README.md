[![Build](https://img.shields.io/github/actions/workflow/status/alexdlaird/java-ngrok-example-play/build.yml)](https://github.com/alexdlaird/java-ngrok-example-play/actions/workflows/build.yml)
![GitHub License](https://img.shields.io/github/license/alexdlaird/java-ngrok-example-play)

# java-ngrok Example - Play (Scala)

This is an example project that shows how to easily integrate [`java-ngrok`](https://github.com/alexdlaird/java-ngrok)
with [Play](https://www.playframework.com/).

## Application Integration

Register an eager `Singleton` in [the app's base `Module`](https://github.com/alexdlaird/java-ngrok-example-play/blob/main/app/Module.scala).

```scala
class Module extends AbstractModule {
  override def configure(): Unit = {
    bind(classOf[NgrokApplicationLifecycle]).asEagerSingleton()
  }
}
```

Then create a [`NgrokApplicatinLifecycle` class](https://github.com/alexdlaird/java-ngrok-example-play/blob/main/app/services/NgrokApplicationLifecycle.scala).
If `ngrok.enabled` config flag is set, we want to initialize `java-ngrok` when Play is booting in a `dev` environment.

```scala
@Singleton
class NgrokApplicationLifecycle @Inject()(config: Configuration, lifecycle: ApplicationLifecycle) {
  private val environment: String = config.getOptional[String]("environment").getOrElse("production")
  private val ngrokEnabled: Boolean = config.getOptional[Boolean]("ngrok.enabled").getOrElse(false)

  // java-ngrok will only be installed, and should only ever be initialized, in a dev environment
  if (environment.equals("dev") && ngrokEnabled) {
    val ngrokClient: NgrokClient = new NgrokClient.Builder()
      .build

    val port: Int = config.getOptional[Int]("http.port").getOrElse(9000)

    val createTunnel: CreateTunnel = new CreateTunnel.Builder()
      .withAddr(port)
      .build
    val tunnel: Tunnel = ngrokClient.connect(createTunnel)

    println(s" * ngrok tunnel \"${tunnel.getPublicUrl}\" -> \"http://localhost:$port\"")
  }
}
```

Pass parameters to our Play application through
[our config file](https://github.com/alexdlaird/java-ngrok-example-play/blob/main/conf/application.conf) (including
making `.ngrok.io` an allowed host):

```
ngrok {
    enabled=true
}
play.filters.hosts {
  allowed = [".ngrok.io", "localhost:9000"]
}
```

Now Play can be started by the usual means, setting `ngrok.enabled` in the config to open a tunnel.

1. Run `make build` to build the application
1. Start application with `sbt run`
1. Check the logs for the `ngrok` tunnel's public URL, which should tunnel to  `http://localhost:9000`
