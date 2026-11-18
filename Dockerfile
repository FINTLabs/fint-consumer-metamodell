# This branch keeps the code of image consumer-metamodell:build.8 (commit e76643d, March 2020),
# which beta runs. The releases 1.0.0 and 1.0.1 do not start: springfox 3.0.0 needs Spring 5,
# and this consumer uses Spring Boot 1.5.
# Thus this Dockerfile takes the build.8 jars from the old image
# and puts them on the eclipse-temurin 8 runtime.
FROM fintlabsacr.azurecr.io/consumer-metamodell:build.8 AS release

FROM eclipse-temurin:8-jre
ENV JAVA_TOOL_OPTIONS="-XX:+ExitOnOutOfMemoryError"
COPY --from=release /data/ /data/
ENTRYPOINT ["java", "-jar", "/data/fint-consumer-metamodell.jar"]
