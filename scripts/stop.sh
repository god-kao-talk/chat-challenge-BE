#!/bin/bash

ROOT_PATH="/home/ubuntu/spring-github-action"
JAR="$ROOT_PATH/application-plain.jar"
STOP_LOG="$ROOT_PATH/stop.log"
SERVICE_PID=$(pgrep -f $JAR) # 실행중인 Spring 서버의 PID

NOW=$(date +%c)


if [ -z "$SERVICE_PID" ]; then
  echo " [$NOW] 서비스 NouFound" >> $STOP_LOG
else
  echo " [$NOW] [$SERVICE_PID] 서비스 종료 " >> $STOP_LOG
  kill "$SERVICE_PID"
  # kill -9 $SERVICE_PID # 강제 종료를 하고 싶다면 이 명령어 사용
fi
