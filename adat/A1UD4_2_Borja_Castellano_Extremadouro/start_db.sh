#!/bin/bash

cd mysql-db || exit 1

docker build -t mysql-1:adat -f "mysql.dockerfile" .
docker run --name mysql-adat --rm -p 3306:3306 mysql-1:adat