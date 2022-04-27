#!/bin/bash
PWDPATH=$(dirname "$0")

# 应用目录
APP_HOME=$(cd "$PWDPATH" && cd .. && pwd)
# shellcheck disable=SC2154

#应用名
APP_NAME=${project.artifactId}

#应用 id
APP_ID=${project.artifactId}-${project.version}.jar

#日志目录
APP_LOG_DIR="$APP_HOME/logs"

#默认生效配置


cd "$APP_HOME" || exit
JVM_OPTS="
 -server
 -Xms4g
 -Xmx4g
 -XX:+UseG1GC
 -XX:MaxGCPauseMillis=200
 -XX:+DisableExplicitGC
 -XX:+PrintGCDetails
 -XX:-UseGCOverheadLimit
 -XX:+HeapDumpOnOutOfMemoryError
 -XX:+PrintGCDateStamps
 -Xloggc:logs/gc.log
"
if [ ! -d "$APP_LOG_DIR" ]; then
        mkdir "$APP_LOG_DIR"
fi

usage() {
	echo "usage: sh boot.sh start|stop|status|restart"
}

alive(){
  pid=$(pgrep -f "$APP_ID")
  #如果存活返回 0，死亡返回0
  if [ -z "${pid}" ]; then
    return 1
  else
    return 0
  fi
}

start() {
  alive
  if [[ $? -eq 0 ]]; then
    echo "$APP_NAME is already running in pid:$pid"
  else
    nohup java $JVM_OPTS -jar $APP_HOME/lib/$APP_ID >> "$APP_HOME/logs/console.log" 2>> "$APP_HOME/logs/console.log" &
  fi
}

stop() {
  alive
  if [[ $? -eq 0 ]]; then
    kill -9 $pid
  else
    echo "$APP_NAME is not running"
  fi
}

status() {
  alive
  if [[ $? -eq 0 ]]; then
    echo "$APP_NAME is running"
  else
    echo "$APP_NAME is not running"
  fi
}

restart() {
  echo stopping "$APP_NAME"
  stop
  echo "$APP_NAME" stopped
  sleep 5
  echo start "$APP_NAME"
  start
  echo "$APP_NAME" started
}

case "$1" in

"start")
  start
  ;;

"stop")
  stop
  ;;

"status")
  status
  ;;

"restart")
  restart
  ;;

*)
	usage
	;;

esac