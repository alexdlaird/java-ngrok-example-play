.PHONY: all build clean install test

SHELL := /usr/bin/env bash
 
all: test

build:
	sbt compile package

clean:
	sbt clean

test:
	sbt test
