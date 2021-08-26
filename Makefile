.PHONY: all build clean install test

SHELL := /usr/bin/env bash

all: build

build:
	sbt compile package

clean:
	sbt clean

test:
	sbt test
