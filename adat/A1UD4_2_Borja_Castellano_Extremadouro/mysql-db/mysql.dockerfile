FROM docker.io/mysql

ENV MYSQL_ROOT_PASSWORD=abc123.
ENV MYSQL_DATABASE=test_db
ENV MYSQL_USER=test_user
ENV MYSQL_PASSWORD=abc123.

EXPOSE 3306

COPY db_creation.sql /docker-entrypoint-initdb.d/

CMD ["mysqld"]
