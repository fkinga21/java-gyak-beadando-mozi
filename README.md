# Művészfilm Mozik

Spring Boot webalkalmazás egy több városban mozit üzemeltető cég filmjeinek, mozijainak és
előadásainak (1999) böngészésére. Java gyakorlat beadandó.

**Készítették:** Fődi Kinga (EW67RO), Lőrik Levente (AICYHB)

## Technológiák

- Java 27, Spring Boot 4.1 (Web MVC, Data JPA, Security, Validation)
- Thymeleaf + `thymeleaf-extras-springsecurity6`
- MySQL (`mysql-connector-j`)
- Bootswatch Darkly (Bootstrap 5) téma, Chart.js diagramok (CDN-ről)

## Futtatás

### Előfeltételek

- JDK 27 (a `pom.xml` `java.version` értéke szerint)
- Futó MySQL szerver a `3306`-os porton, `mozi` nevű adatbázissal

### Adatbázis

A kapcsolat beállításai az [`application.properties`](src/main/resources/application.properties)
fájlban vannak:

| Beállítás | Érték |
|---|---|
| Host | `MYSQL_HOST` környezeti változó, alapértelmezés: `localhost` |
| Adatbázis | `mozi` |
| Felhasználó / jelszó | `root` / üres |

A `film`, `mozi` és `eloadas` táblákat az adatokkal együtt előre be kell tölteni, a repó nem
tartalmazza őket. A `users` és `uzenetek` táblákat a Hibernate hozza létre induláskor
(`spring.jpa.hibernate.ddl-auto=update`).

| Tábla | Oszlopok |
|---|---|
| `film` | `id`, `cim`, `ev`, `hossz` |
| `mozi` | `id`, `nev`, `varos`, `ferohely` |
| `eloadas` | `id`, `filmid` → `film.id`, `moziid` → `mozi.id`, `datum`, `nezoszam`, `bevetel` |

### Indítás

```sh
./mvnw spring-boot:run        # Linux / macOS / Git Bash
mvnw.cmd spring-boot:run      # Windows
```

Az alkalmazás a <http://localhost:8080> címen érhető el.

### Admin felhasználó

Regisztrációkor minden felhasználó `USER` szerepkört kap. Admin felhasználót így lehet
létrehozni: regisztrálj az oldalon, majd az adatbázisban állítsd át a szerepkört:

```sql
UPDATE users SET role = 'ADMIN' WHERE username = 'felhasznalonev';
```

## Menük és jogosultságok

| Menü | URL | Ki éri el | Tartalom |
|---|---|---|---|
| Főoldal | `/` | mindenki | Bemutatkozás, filmek / mozik / előadások száma |
| Adatbázis | `/adatbazis` | mindenki | A három tábla adatai, az előadások lapozva (20 / oldal) |
| Kapcsolat | `/kapcsolat` | mindenki | Üzenetküldő űrlap szerver oldali validációval |
| Diagram | `/diagram` | mindenki | Városonkénti nézőszám és filmenkénti bevétel (top 10), Chart.js |
| REST | `/rest` | mindenki | A REST API leírása |
| Üzenetek | `/uzenetek` | bejelentkezett felhasználó | A beküldött üzenetek, legújabb elöl |
| CRUD | `/crud` | admin | Filmek listázása, felvétele, módosítása, törlése |
| Regisztráció / Belépés | `/register`, `/login` | vendég | |

## REST API

A mozik adatai JSON formátumban érhetők el. Az olvasás nyitott, a módosító kérésekhez admin
felhasználó kell HTTP Basic hitelesítéssel.

| Metódus | URL | Leírás | Jogosultság |
|---|---|---|---|
| GET | `/api/mozik` | Az összes mozi | nyitott |
| GET | `/api/mozik/{id}` | Egy mozi | nyitott |
| POST | `/api/mozik` | Új mozi | admin |
| PUT | `/api/mozik/{id}` | Mozi módosítása | admin |
| DELETE | `/api/mozik/{id}` | Mozi törlése (ha nincs hozzá előadás) | admin |

Példa:

```sh
curl -u admin:jelszo -H "Content-Type: application/json" \
     -d '{"nev": "Teszt Mozi", "varos": "Szolnok", "ferohely": 120}' \
     http://localhost:8080/api/mozik
```

Hibás adatoknál `400`-at ad vissza mezőnkénti hibaüzenetekkel. Nem létező mozinál `404`-et,
előadáshoz kötött mozi törlésekor `409`-et.

## Projekt felépítése

```
src/main/java/com/example/mozi/
├── config/        Spring Security beállítások
├── controller/    MVC és REST kontrollerek
├── entity/        JPA entitások (Film, Mozi, Eloadas, User, Uzenet)
├── repository/    Spring Data JPA repository-k
└── service/       Felhasználók betöltése a belépéshez
src/main/resources/
├── templates/     Thymeleaf oldalak (közös elrendezés: layout.html)
└── static/css/    Saját stílusok
```
