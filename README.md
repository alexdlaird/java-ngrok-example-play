[![Build](https://img.shields.io/github/actions/workflow/status/alexdlaird/java-ngrok-example-play/build.yml)](https://github.com/alexdlaird/java-ngrok-example-play/actions/workflows/build.yml)
![GitHub License](https://img.shields.io/github/license/alexdlaird/java-ngrok-example-play)

# java-ngrok Example - Play (Scala)

This is an example project that shows how to easily integrate [`java-ngrok`](https://github.com/alexdlaird/java-ngrok)
with [Play](https://www.playframework.com/), as [summarized in the docs](https://alexdlaird.github.io/java-ngrok/integration/#play-scala).

## Running

To run the example locally (Play 2.8 requires JDK 11), build it, then start it in dev mode, which opens a tunnel since
`ngrok.enabled` is set in `conf/application.conf`. Play starts the application on the first request, so request
`http://localhost:9000/healthcheck` once, after which the tunnel's public URL is printed.

```sh
make build
NGROK_AUTHTOKEN=$NGROK_AUTHTOKEN sbt run
```

To run the tests, use `make test`.
