docker build . -t ordersystem
docker run --rm -it -e DBPASS=pass -p8080:8080  ordersystem
