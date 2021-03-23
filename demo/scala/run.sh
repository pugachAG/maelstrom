#!/bin/bash

DIR="$( cd "$( dirname "${BASH_SOURCE[0]}" )" &> /dev/null && pwd )"
scala $DIR/target/scala-2.12/scala-demo-assembly-0.1.jar
