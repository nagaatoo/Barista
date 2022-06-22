call ./gradlew build -x test
call docker build --tag=barista:latest .
call docker run -p 8551:8551 barista:latest