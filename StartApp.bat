docker build . -t ordersystem
docker run --rm -it --name ordersystem -e DBPASS=pass -p8080:8080  ordersystem
