# Testausdokumentti
Ohjelmaa on testattu JUnitilla yksikkö- ja integraatiotestein, minkä 
lisäksi olen testannut sovelluksen toimintaa laajasti käytännön testein.

## Yksikkö- ja integraatiotestaus
Sovelluslogiikkaa on testattu laajasti JUnit-testeillä, mutta 
käyttöliittymästä vastaavaa luokkaa PuyoPuyoUi ei ole testattu. Lisäksi 
enum-tyyppinen Colour-luokka on testaamatta.

### Testauskattavuus
Sovelluksen testien kattavuutta on mitattu Jacocon avulla. Alla on kuva Jacoco-testikattavuusraportista. Kattavuusraportissa ei ole otettu käyttöliittymästä vastaavaa luokkaa huomioon.
![alt text](https://github.com/villekov1/otm-harjoitustyo/blob/master/dokumentointi/kuvat/jacocotestikattavuus.JPG "Jacoco-testikattavuusraportti")

## Järjestelmätestaus
Sovellusta on testattu manuaalisesti sekä Windows 10- että 
Linux-ympäristössä.

## Sovellukseen jääneet ongelmat
