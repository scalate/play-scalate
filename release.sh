#!/bin/bash
sbt \
  clean \
  "+ plugin/publishSigned" \
  sonatypeRelease
