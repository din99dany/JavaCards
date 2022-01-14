# Documentatie aplicatie cartonase

## Cereri de afacere

* Ca si user as vrea sa imi fac cont 
* Ca si user as vrea sa pot creea un anunt
* Ca si user as vrea sa pot organiza o licitatie
* Ca si user as vrea sa pot face o oferta la licitatie
* Ca si user as vrea sa pot sterge/oprii un anunt/ o licitatie
* Ca si user as vrea sa pot contacta un alt user
* Ca si user as vrea sa pot cauta o anumita carte sa vad daca exista in colectie
* Ca si user as vrea sa pot vedea anunturile unei carti
* Ca si administrator as vrea sa pot adauga cartonase noi 
* Ca si administrator as vrea sa pot termina conturile utilizatorilor care incalca termenii de utilizare


## Functionalitati derivate
API-ul satisface urmatoarele functionalitati
* Management de utilizatori
* Functionalitate de comert ( anunt/licitatie )
* Functionalitate de mesagerie
* Functionalitate de indexare a cartonaselor

## Endpoint-uri
* /api
  * /users
    * GET : returneaza lista utilizatorilor 
    * POST : este necesar sa avem in body parametrii `name` , `email` si `password`
    * /users/{id}
      * DELETE : unde `id` este id-ul userului de sters
  * /play_card
    * GET : returneaza o lista a cartilor din colectie. are parametrul de query optional `search` care reprezinta un sirul de caractere dupa care se va face cautarea
    * POST : folosit pentru a crea un nou cartonas. Avem pamaetrii pe `body` urmatorii : `name`, `role`, `collection`
    * /play_card/{id}
      * DELETE : sterge cartonasul cu `id` daca exista
      * GET    : returneaza anunturile pentru cartonasul cu `id` daca exista
  * /chat
    * GET : returneaza o lista cu toate mesajele
    * POST : creaza un nou mesaj. In `body` trebuie sa avem parametrii `senderId`, `receiverId` precum si`message` 
    * /chat/{id}
      * DELETE : sterge chat-ul cu `id`
      * GET : returneaza o lista cu mesajele utilizatorului cu `id`
  * /sell_add
    * GET : returneaza o lista cu toate anunturile 
    * POST : creaza un anunt nou. Parametrii de query `sellerId`, `cardId`, `price` si `description`
    * /sell_add/{id}
      * DELETE : sterge anuntul cu `id`
  * /auction
    * GET : returneaza o lista cu toate licitatile 
    * POST : creaza o noua licitatie. In header trebuie sa avem `xAddId` care este `id`-ul anuntului pe care vrem sa-l transformam in licitatie. Pentru a avea o licitatie trebuie sa avem mai intai un anunt. Pretul de la care porneste licitatia este pretul anuntului.
    * /auction/{id}
      * DELETE : sterge licitatia cu `id` dar pastreaza anuntul.
      * GET : returneaza o lista cu ofertele din cadrul licitatiei cu id-ul `id`
      * POST : creaza o noua oferta  in cadrul licitatiei `id`. Ca si parametrii de query avem `offererId`,  id-ul utilizatorului care face oferta precum si `price`, oferta in sine. Pentru ca o oferta sa fie valida, aceasta trebuie sa fie cu 5% mai mult decat oferta anterioara.


## Schema baza de date

```
--------+
|        |
|        |
| Chat   |
|        |<-------+
|        |        |
|        |        |
+--------+        |
    ^             |
    |             |
    |        +----+----+
    |        |         |           +--------+           +--------+
    |        |         |           |        |           |        |
    |        |   User  +----------->        |           |        |
    +--------+         |           | Add    |           | Play   |
             |         |           |        |<----------+ Card   |
             |         |           |        |           |        |
             +----+----+           |        |           |        |
                  |                +----+---+           +--------+
                  |                     |
                  |                     |
                  |                     |
                  |                     |
                  |                     |
                  |                     |
             +----v----+                |
             |         |            +---v----+
             |         |            |        |
             | Offer   |            |        |
             |         |<-----------+ Auction|
             |         |            |        |
             |         |            |        |
             +---------+            |        |
                                    +--------+


```

