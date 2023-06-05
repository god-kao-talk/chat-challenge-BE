#!/bin/bash

ROOT_PATH="/home/ubuntu/spring-github-action"
JAR="$ROOT_PATH/application-plain.jar"
CONTAINER="chat-challenge"
TAG="latest"

APP_LOG="$ROOT_PATH/application.log"
ERROR_LOG="$ROOT_PATH/error.log"
START_LOG="$ROOT_PATH/start.log"
PROFILES_ACTIVE="Dspring.profiles.active=dev"

NOW=$(date +%c)

echo "[$NOW] $JAR 복사" >> $START_LOG
cp $ROOT_PATH/build/libs/chat-0.0.1-SNAPSHOT.jar $JAR

# echo "[$NOW] > $JAR 실행" >> $START_LOG
# nohup java -jar -$PROFILES_ACTIVE $JAR > $APP_LOG 2> $ERROR_LOG &

echo "[$NOW] JIB 도커 빌드" >> $START_LOG
cd $ROOT_PATH
.\gradlew jibDockerBuild

echo "[$NOW] > $CONTAINER 실행" >> $START_LOG
docker run -d -p 8080:8080 --name $CONTAINER $CONTAINER:$TAG

SERVICE_PID=$(pgrep -f $JAR)
echo "[$NOW] > 서비스 PID: $SERVICE_PID" >> $START_LOG
