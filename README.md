[![CI/CD](https://github.com/alexdlaird/java-ngrok-example-scala/workflows/CI/CD/badge.svg)](https://github.com/alexdlaird/java-ngrok-example-play/actions?query=workflow%3ACI%2FCD)
![GitHub License](https://img.shields.io/github/license/alexdlaird/java-ngrok-example-scala)

# java-ngrok Example - Play (Scala)

This is an example project that shows how to easily integrate [`java-ngrok`](https://github.com/alexdlaird/java-ngrok)
with [Play](https://www.playframework.com/).

## Configuration

Create
a [`NgrokConfiguration`]()
class that lets us use the config to enable `ngrok` and pass it some useful parameters.

```java

```

And pass parameters to our Play application through
[our config file]():

```
ngrok.enabled=true
```

## Application Integration

If `ngrok.enabled` config flag is set, we want to initialize `java-ngrok` when Play is booting. An easy place to do
this is in the `run()` method of [the Application]().

```java

```

Now Play can be started by the usual means, setting `ngrok.enabled` in the config to open a tunnel.

1. Run `make build` to build the application
1. Start application with `sbt run`
1. Check the logs for the `ngrok` tunnel's public URL, which should tunnel to  `http://localhost:9000`
