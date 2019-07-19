# Seeder-Tracker-Leecher
Basic "Torrent" implementation.

Az alapfeladat során a következő futtatható osztályokat implementáljuk:

Tracker: A rácsatlakozó csomópontoknak elküldi, hogy mely fájlokat mely csomópontok tárolják.
Seeder: Regisztrálja a Tracker-ben az általa tárolt fájlok neveihez a saját portját, majd kiszolgálja a rácsatlakozó csomópontok kéréseit.
Leecher: A Tracker-től lekéri valamely fájlt tároló Seeder csomópont portját, csatlakozik a Seeder-hez és letölti tőle a fájlt.
Az egyszerűség kedvéért a fájlneveket és a fájlokat is String-ekkel, a hálózat csomópontjait pedig a portjaikkal (Integer) reprezentáljuk. Feltesszük, hogy egy fájlnévvel pontosan egy csomópont tárol fájlt.

Az egyes osztályok szerkezetére és viselkedésére az alábbi követelmények vonatkoznak:

#Tracker

Egy fájlnévhez portokat rendelő asszociatív adatszerkezetben tartja számon, hogy melyik Leecher csomópont melyik fájlt tárolja. Az adatszerkezet legyen objektumszintű attribútum.
void storeFileId(String fileId, Integer peerPort) metódus hozzárendel az adatszerkezetben egy fájlnévhez egy portot.
Integer lookupPeerPort(String fileId) metódus megkeresi az adatszerkezetben, hogy egy adott fájlnevet tároló csomópont mely porton érhető el.
void start() metódus egymás után fogad tetszőlegesen sok csomópontot az 55555 porton, akikkel a következő protokoll szerint kommunikál:

Amennyiben a leech üzenetet kapja a csomóponttól, vár egy további, fájlnevet tartalmazó üzenetet, majd visszaküldi a fájlnévhez fájlt tároló csomópont portját.
Amennyiben a seed üzenetet kapja a csomóponttól, vár egy további, egész szám portot tartalmazó üzenetet, majd tetszőlegesen sok, fájlnevet tartalmazó üzenetet vár. Minden kapott fájlnévhez eltárolja az előzőleg kapott portot.
A kiszolgálást követően bontja a kapcsolatot a csomóponttal, és várja a következő felcsatlakozó csomópontot.
A main metódus belépési pont példányosít egy Tracker objektumot és elindítja azt a start metódussal. Parancssori argumentumokat nem vár.

#Seeder
Parancssori argumentumok:

Az első parancssori argumentum egy egész szám port, amelyre majd a letölteni szándékozó csomópontok csatlakozhatnak.
A további páros darab további argumentum fájlnév-fájl párok sorozata (pl. k1 v1 k2 v2 k3 v3), ahol a fájlneveket (pl. k1) az általuk jelölt fájl tartalma (pl. v1) követi.
A fájlnevekhez tartozó fájlokat egy String-hez String-et rendelő asszociatív adatszerkezetben tartja számon.

Futása során sorban a következő két lépést hajtja végre:

Regisztrálja az 55555 porton várakozó Tracker-be a fájlneveket a saját portjával. Ehhez először elküldi a seed üzenetet, majd a saját portját, majd elküldi a fájlneveket (pl. k1, k2, k3). A küldés után lezárja a kapcsolatot.
Várakozik felcsatlakozó csomópontokra a saját portján. A felcsatlakozott csomóponttól egy fájlnevet kap (pl. k1), válaszul pedig visszaküldi a fájlnévhez tartozó fájlt (pl. v1). A küldés után lezárja a kapcsolatot.

#Leecher
Parancssori argumentum:

Egy fájlnév (pl. k1).
Futása során sorban a következő két lépést hajtja végre:

Lekéri az 55555 porton várakozó Tracker-től a fájlnévhez tartozó fájlt tároló csomópont portját. Ehhez először elküldi a leech üzenetet, aztán a fájlnevet, majd vár egy egész szám portot tartalmazó üzenetet. Az üzenet érkezése után lezárja a kapcsolatot.
Felcsatlakozik a kapott porton várakozó Seeder csomópontra és letölti tőle a fájlnévhez tartozó fájlt. Ehhez elküldi a fájlnevet, majd várja a fájlt reprezentáló String üzenetet. A fájl beérkezése után lezárja a kapcsolatot.
