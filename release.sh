#!/bin/bash
sbt \
  clean \
  ++2.12.17 \
  plugin/publishSigned \
  clean \
  ++2.13.8 \
  plugin/publishSigned \
  sonatypeRelease
