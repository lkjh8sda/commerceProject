docker run -d \
--name commerceProject \
-e MYSQL_ROOT_PASSWORD="1234" \
-e MYSQL_USER="commerceProject" \
-e MYSQL_PASSWORD="1234" \
-e MYSQL_DATABASE="commerceProject" \
-p 3306:3306 \
mysql:latest