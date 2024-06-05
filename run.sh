#!/bin/bash
DATABASE_DOCKER_NAME="mysql"
DATABASE_DOCKER_USER="sorravit"
DATABASE_DOCKER_PASSWORD="sorravit"
DATABASE_DOCKER_DATABASE_NAME="big"
DATABASE_DOCKER_ROOT_PASSWORD="sorravit"

goal_database_start() {
  docker run --detach --env MYSQL_ROOT_PASSWORD=${DATABASE_DOCKER_ROOT_PASSWORD} --env MYSQL_USER=${DATABASE_DOCKER_USER} --env MYSQL_PASSWORD=${DATABASE_DOCKER_PASSWORD} --env MYSQL_DATABASE=${DATABASE_DOCKER_DATABASE_NAME} --name ${DATABASE_DOCKER_NAME} --publish 3306:3306 mysql
}
goal_database_stop() {
  docker stop ${DATABASE_DOCKER_NAME}
}
goal_database_clear() {
  docker stop ${DATABASE_DOCKER_NAME}
  docker rm ${DATABASE_DOCKER_NAME}
}
if type -t "goal_$1" &>/dev/null; then
  goal_$1 ${@:-2}
else
  echo "usage: $0 <goal>
    goal:
        database_start                      -- start database from mysql docker image
        database_stop                       -- stop database from mysql docker image
        database_clear                      -- stop and remove database from mysql docker image
        "
  exit 1
fi
