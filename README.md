# Order System (Backend)

Egy rendelési rendszer "frontend"-jének a kiszolgálását teszi lehetőve a program. Vevők/eladók, termékek, valamint a rendelések adatainal nyilvántartását teszi lehetővé."

A program Docker konténerben fut.
## A program inditása:
###    ./StartApp.bat  vagy StartApp.bat - Windows operációs rendszer estén.
###    ./StartApp.sh   - Linux operációs rendszer estén.
Ekkor megtörténik a program fordítása, illetve a Docker konténerben elindul a program. A 8080-as porton várja a hívásokat.

# Alkalmazott technológia:
- Spring-boot
- java JPA
- Hibernate
- H2 adatbázis
- Flyway
- Docker

## Dto-ok
- CustomerDTO: vevő beérkező adatok
- ProductDTO: termék beérkező adatok
- OrderDTO: rendelés beérkező adatok
- OrderItemDTO: rendelés tétel adatok OrderDTO-hoz
- OrderResponseDTO: rendelés válasz adatok
- OrderItemResponseDTO: rendelés tétel adatok OrderResponseDTO-hoz
- OrderSumDTO: rendelés érték szerinti összesítő válasz adatok vevőnként

## Entity-k (Entitások)
- Customer: vevő/eladó törzs
- Product: terméke törzs
- Order: rendelések
- OrderItem: rendelés tételei

## Endpoint-ok (Végpontok)
### Customer (Vevő/eladó)
- Get: /customer: visszaadja az összes vevőt/eladót
- Get: /customer/id: visszaadja az adott id-hez tartozó vevőt/eladót
- Post: /customer: customerDTO-t vár, elmenti, ill. visszaadja az elmentett vevőt/eladót
- Put: /customer/id: id-t, customerDTO-t vár, felulírja, ill. visszaadja az elmentett vevőt/eladót
- Delete: /customer/id: törli a megadott vevőt/eladót

### Product (Termék)
- Get: /product: visszaadja az összes terméket
- Get: /product/id: visszaadja az adott id-hez tartozó terméket
- Post: /product: productDTO-t vár, elmenti, ill. visszaadja az elmentett terméket
- Put: /product/id: id-t, productDTO-t vár, felulírja, ill. visszaadja az elmentett terméket
- Delete: /product/id: törli a megadott terméket

### Order (Rendelés)
- Get: /orders: visszaadja az összes rendelést
- Get: /orders/id: visszaadja az adott id-hez tartozó rendelést
- Get: /orders/sellerId/seller: visszaadja egy adott eladóhoz tartozó rendeléseket
- Get: /orders/buyerId/buyer: visszaadja egy adott vevőhöz tartozó rendeléseket
- Get: /orders/toship: visszaadja a kiszállítandó rendeléseket
- Get: /orders/buyersum: visszaadja vevőnként az összes rendelés értéket (opcionális parameter: buyerID - ekkor csak az adott vevőre gyűjti ki az adatot) 
- Post: /orders: productDTO-t vár, elmenti, ill. visszaadja az elmentett rendelést
- Put: /orders/id: id-t, productDTO-t vár, felulírja, ill. visszaadja az elmentett rendelést
- Put: /orders/id/shipping id-t vár, az adott order-t átállít kiszállítottra, visszaadja az elmentett rendelést 
- Delete: /orders/id: törli a megadott id-jű rendelést

## Teszt 
A program 2 Unit tesztet tartalmaz (Customer, Product), illetve 3 integrációs tesztet (Customer, Product, Order).

Indítás:

./OrderTest.bat vagy OrderTest.bat (Windows)

./OrderTest.sh (Linux)   

Swagger segítségével minden végpont tesztelhető.

Indítás: http://localhost:8080/swagger-ui.html

Postman alkalmazás segítségével a végpontok szintén tesztelhetőek.

