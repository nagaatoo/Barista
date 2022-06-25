call ./gradlew build -x test
call docker build --tag=barista:latest .
call docker run -p 8551:8551 -v /home/nagatoo/baristaData:/mnt/baristaData --add-host=database:172.17.0.1 --name barista barista:latest