# Order System (Backend)

Egy rendelési rendszer "frontend"-jének a kiszolgálását teszi lehetőve a program."
A program Docker konténerben fut.
## Inditás:
###    StartApp.bat

# Technológia:
- Spring-boot
- java JPA
- Hibernate
- H2 adatbázis
- Docker

## Dto-ok
- CustomerDTO: vevő beérkező adatok
- ProductDTO: termék beérkező adatok
- OrderDTO: rendelés beérkező adatok
- OrderItemDTO: rendelés tétel beérkező adatok
- OrderResponseDTO: rendelés válasz adatok
- OrderItemResponseDTO: rendelés tétel válasz adatok

## Entity-k
- Customer: vevő/eladó törzs
- Product: terméke törzs
- Order: rendelések
- OrderItem: rendelés tételei

## Endpoint-ok
### Customer
- Get/customer: visszaadja az összes vevőt/eladót
- Get/customer/id: visszaadja az adott id-hez tartozó vevőt/eladót
- Post/customer: customerDTO-t vár, elmenti, ill. visszaadja az elmentett vevőt/eladót
- Put/customer/id: id-t, customerDTO-t vár, felulírja, ill. visszaadja az elmentett vevőt/eladót
- Delete/customer/id: törli a megadott vevőt/eladót

### Product
- Get/product: visszaadja az összes terméket
- Get/product/id: visszaadja az adott id-hez tartozó terméket
- Post/product: productDTO-t vár, elmenti, ill. visszaadja az elmentett terméket
- Put/product/id: id-t, productDTO-t vár, felulírja, ill. visszaadja az elmentett terméket
- Delete/product/id: törli a megadott terméket

### Order
- Get/orders: visszaadja az összes rendelést
- Get/orders/id: visszaadja az adott id-hez tartozó rendelést
- Get/orders/sellerId/seller: visszaadja egy adott eladóhoz tartozó rendeléseket
- Get/orders/buyerId/buyer: visszaadja egy adott vevőhöz tartozó rendeléseket
- Get/orders/toship: visszaadja a kiszállítandó rendeléseket
- Post/orders: productDTO-t vár, elmenti, ill. visszaadja az elmentett rendelést
- Put/orders/id: id-t, productDTO-t vár, felulírja, ill. visszaadja az elmentett rendelést
- Put/orders/id/shipping id-t vár, az adott order-t átállít kiszállítottra, visszaadja az elmentett rendelést 
- Delete/orders/id: törli a megadott id-jű rendelést
