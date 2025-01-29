#!/bin/bash

cd mysql-db || exit 1

podman build -t mysql-1:adat -f "mysql.dockerfile" .
podman run --name mysql-adat -d --rm -p 3306:3306 mysql-1:adat &