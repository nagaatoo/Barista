#!/bin/bash
./gradlew build -x test
docker rm barista
docker build --tag=barista:latest .
docker run -p 8551:8551 -v /home/nagatoo/baristaData:/mnt/baristaData --add-host=database:172.17.0.1 --name barista barista:latest
