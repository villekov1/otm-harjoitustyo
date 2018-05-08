# OTM-harjoitustyö: Puyo Puyo -tyylinen peli
Sovellus on pulmapeli, joka mukailee Puyo Puyo -nimistä peliä. Pelissä 
kentän katosta tippuu kahden värillisen Puyon muodostamia kappaleita, 
joita voi liikuttaa ja kääntää. Vähintään neljän samanvärisen Puyon 
muodostamat ketjut katoavat, jolloin pelaaja saa pisteitä.

## Dokumentaatio
[Tuntikirjanpito](https://github.com/villekov1/otm-harjoitustyo/blob/master/dokumentointi/tuntikirjanpito.md)

[Vaatimusmäärittely](https://github.com/villekov1/otm-harjoitustyo/blob/master/dokumentointi/vaatimusmaarittely.md)

[Arkkitehtuurikuvaus](https://github.com/villekov1/otm-harjoitustyo/blob/master/dokumentointi/arkkitehtuuri.md)

[Käyttöohje](https://github.com/villekov1/otm-harjoitustyo/blob/master/dokumentointi/kayttoohje.md)

[Testausraportti](https://github.com/villekov1/otm-harjoitustyo/blob/master/dokumentointi/testaus.md)

## Releaset
[Viikko 5](https://github.com/villekov1/otm-harjoitustyo/releases/tag/Release1)

[Viikko 6](https://github.com/villekov1/otm-harjoitustyo/releases/tag/viikko6)

## Huomioitavaa
Sovellus hyödyntää SQLite-tietokantoja, joten SQLiten on oltava asennettuna tietokoneelle.

## Komentorivitoiminnot:
Ohjelma on kirjoitettu Java-kielellä, joten se hyödyntää Mavenia, 
Jacocoa, checkstylea ja niiden komentoja.

### Testaus
Testit suoritetaan joko Netbeansin kautta tai komennolla: *mvn test* 

Testikattavuusraportin voi luoda komennolla: *mvn jacoco:report*.
Raportti syntyy index.html -nimellä kansioon target/site/jacoco, ja sen 
voi avata selaimella.

### Jar-tiedoston luominen
Komento *mvn package* luo target-kansioon suoritettavan jar-tiedoston 
PuyoPuyoPeli-1.0-SNAPSHOT. Jar-tiedoston voi suorittaa komennolla *java 
-jar PuyoPuyoPeli-1.0-SNAPSHOT.jar*.

Jar-tiedosto on saatavilla myös viikon 5 ja 6 julkaisuissa nimellä *PuyoPuyoPeli.jar*.

### Checkstyle
Checkstyle-raportin voi luoda komennolla *mvn jxr:jxr 
checkstyle:checkstyle*. Tämä luo checkstyle.html -tiedoston kansioon 
target/site.

### JavaDoc
JavaDoc-dokumentoinnin saa luotua projektin kansiossa komennolla *mvn 
javadoc:javadoc*. Tämä luo index.html -tiedoston kansioon 
target/site/apidocs.
