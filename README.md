# OTM-harjoitustyö: Puyo Puyo -tyylinen peli
Sovellus on pulmapeli, joka mukailee Puyo Puyo -nimistä peliä. Pelissä 
kentän katosta tippuu kahden värillisen Puyon muodostamia kappaleita, 
joita voi liikuttaa ja kääntää. Vähintään neljän samanvärisen Puyon 
muodostamat ketjut katoavat, jolloin pelaaja saa pisteitä.

## Dokumentaatio
[tuntikirjanpito.md](https://github.com/villekov1/otm-harjoitustyo/blob/master/dokumentointi/tuntikirjanpito.md)

[vaatimusmaarittely.md](https://github.com/villekov1/otm-harjoitustyo/blob/master/dokumentointi/vaatimusmaarittely.md)

[arkkitehtuuri.md](https://github.com/villekov1/otm-harjoitustyo/blob/master/dokumentointi/arkkitehtuuri.md)

## Komentorivitoiminnot:
Ohjelma on kirjoitettu Java-kielellä, joten se hyödyntää Mavenia, 
Jacocoa, checkstylea ja niiden komentoja.

# Testaus
Testit suoritetaan joko Netbeansin kautta tai komennolla: *mvn test* 

Testikattavuusraportin voi luoda komennolla: *mvn jacoco:report*
Raportti syntyy index.html -nimellä kansioon target/site/jacoco, ja sen 
voi avata selaimella.



## Jar-tiedoston luominen
Komento *mvn package* luo target-kansioon suoritettavan jar-tiedoston 
PuyoPuyoPeli-1.0-SNAPSHOT. Jar-tiedoston voi suorittaa komennolla java 
-jar PuyoPuyoPeli-1.0-SNAPSHOT.jar.


## Checkstyle
Checkstyle-raportin voi luoda komennolla *mvn jxr:jxr 
checkstyle:checkstyle*. Tämä luo checkstyle.html -tiedoston kansioon 
target/site.
