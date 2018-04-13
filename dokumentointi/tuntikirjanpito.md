# Tuntikirjanpito

| Päivä | Aika (h) | Mitä tein?  |
| :----:|:-----| :-----|
| 20.3. | 2    | Tein sovelluksen vaatimusmäärittelyä. Aloitin projektin koodaamisen tekemällä alustavat Puyo-, Vari- ja Pelitilanne-luokat |
| 22.3  | 3    | Jatkoin vaatimusmäärittelyä ja luokkien koodaamista. Tein alustavan version pelinäkymästä, joka piirtää Puyot. Sain palikat myös tippumaan onnistuneesti.| 
| 24.3  | 2    | Jatkoin sovelluksen koodausta. Lisäsin mahdollisuuden tippuvien Puyojen siirtoon sivuille. Kun Puyo kohtaa maan tai maassa olevan Puyon, se jää paikalleen. Kun molemmat Puyot ovat maassa, arvotaan uusi Puyo. Lisäsin arvotuille Puyoille satunnaisen värin arpovan ominaisuuden.
| 26.3  | 1    | Korjasin ohjelmaa siten, että tippuvia Puyoja ei voi siirtää sivusuunnassa toisten Puyojen läpi. Lisäksi sivuttaisessa suunnassa liikuttaminen vaatii, että kumpikaan Puyo ei ole maassa, jotta kahden Puyon kokonaisuus ei hajoa.|
| 27.3  | 2    | Lisäsin mahdollisuuden tippuvien Puyojen kääntämiseen. Lisäksi lisäsin mahdollisuuden ns. "hard droppiin", jossa tippuvat Puyot tippuvat välittömästi maahan.|
| 29.3  | 2    | Lisäsin pistelaskurin käyttöliittymään. Aloitin ketjujen etsimiseen ja niiden poistoon tarkoitettujen toiminnallisuuksien toteutuksen, mutta ne eivät tällä hetkellä toimi toivotulla tavalla. Lisäksi kadonneiden Puyojen jäljiltä ilmassa olevat Puyot eivät tipu alas.|
| 2.4   | 2    | Parantelin ohjelman toiminnallisuutta ketjujen löytämiseen. Lisäksi Puyojen katoamisen jälkeen ylläolevat Puyot tippuvat alas. Toistaiseksi ohjelmassa on kuitenkin paljon bugeja, eikä ketjujen löytäminen toimi kunnolla.
| 7.4   | 1    | Lisäsin yksinkertaisen alkuvalikon, jossa on nappi pelinäkymään siirtymiseksi sekä nappi huipputuloksiin (ei vielä toteutettu). Lisäksi pelinäkymässä oleva nappi palauttaa takaisin aloitusnäkymään.|
| 8.4   | 1    | Aloitin testien tekemisen. Tein pom.xml-tiedostoon tarvittavat lisäykset. Tein Puyo- ja Pelitilanne -luokille testiluokat. PuyoPuyoUi-luokalle ja Vari-enumille ei tarvitse tehdä testiluokkia, sillä PuyoPuyoUi vastaa vain piirtämisestä, eikä enumissa ole mitään testattavaa. Tein PuyoTestiin testit getSijaintiX- ja getSijaintiY -metodien testaamiseksi.|
| 10.4  | 4    | Korjasin ohjelmassa olleen ison bugin, joka esti ketjujen tuhoamisen kunnolla ja Puyojen tippumisen ketjun tuhoutumisen jälkeen. Lisäsin Puyo-luokalle toString -metodin. Tein myös parannuksia käyttöliittymään lisäämällä "Pysäytä"- ja "Aloita alusta" -napit. Lisäsin alustavan Tulos-luokan huipputuloksien käsittelyyn. Lisäsin myös pisteytysjärjestelmän peliin ja lisäsin alkuvalikkoon lyhyet ohjeet. Tein muutamia testejä lisää.|
| 13.4  | 3    | Aloitin huipputuloksien kirjaamisen käsittelyn. Tein SQLiten avulla tietokannan tulokset.db, jossa on taulu Tulos. Tein TulosDao-luokan ja sille tarvittavat metodit. Lisäksi tein Database-luokan toteutuksineen. Laitoin tietokantaan hieman testidataa, joka näkyy nyt sovelluksen aloitusnäkymässä.|
