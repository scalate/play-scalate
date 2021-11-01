#!/bin/bash
sbt \
  clean \
  ++2.12.15 \
  plugin/publishSigned \
  clean \
  ++2.13.7 \
  plugin/publishSigned \
  sonatypeRelease
