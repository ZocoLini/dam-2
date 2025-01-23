#!/bin/bash

cd mysql-db || exit 1

docker build -t mysql:adat -f "mysql.dockerfile" .
docker run --name mysql-adat -p 3306:3306 mysql:adat &