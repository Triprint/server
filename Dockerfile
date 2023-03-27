FROM openjdk:11
ARG JAR_FILE=build/libs/*.jar
ARG SCRIPT_FILE=scripts/run.sh
COPY ${JAR_FILE} app.jar
COPY ${SCRIPT_FILE} run.sh
ENTRYPOINT ["sh", "run.sh"]
