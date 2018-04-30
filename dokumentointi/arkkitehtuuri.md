# Arkkitehtuurikuvaus

## Rakenne
Sovellus noudattaa karkeasti seuraavanlaista pakkaushierarkiaa. Tarkempi kuvaaja löytyy alla olevasta pakkauskaaviosta.
![pakkaushierarkia](https://github.com/villekov1/otm-harjoitustyo/blob/master/dokumentointi/Pakkaushierarkia.jpg "Pakkaushierarkia")

Pakkaus *PuyoPuyoPeli.ui* vastaa sovelluksen graafisesta käyttöliittymästä. Pakkaus *PuyoPuyoPeli.domain* puolestaan sisältää sovelluslogiikkaan ja sovelluksen toiminnan kannalta keskeisiin käsitteisiin liittyvät luokat. Pakkaus *PuyoPuyoPeli.database* sisältää tietokantatoiminnallisuuteen liittyvät luokat.

## Sovelluslogiikka

### Sovelluksen luokkakaavio:
![luokkakaavio](https://github.com/villekov1/otm-harjoitustyo/blob/master/dokumentointi/UML_luokkakaavio.jpg "Luokkakaavio")

### Pakkauskaavio:
![pakkauskaavio](https://github.com/villekov1/otm-harjoitustyo/blob/master/dokumentointi/Pakkauskaavio.jpg "Pakkauskaavio")

### Netbeansin kautta otettu kuva pakkausrakenteesta:
![pakkauskuva](https://github.com/villekov1/otm-harjoitustyo/blob/master/dokumentointi/Pakkauskuva.JPG "Sovelluksen pakkausrakenne")

## Tietojen tallennus
Tietojen tallennus tapahtuu SQLiten avulla SQL-tietokantaan.

## Sovelluksen päätoiminnallisuudet

### Sekvenssikaavio tilanteesta, jossa käyttäjä siirtää tippuvia Puyoja oikealle
Alla olevassa kuvassa luokan sisäiset metodikutsut on esitetty väliaikaisina palkkeina, jotka tuhotaan suorituksen jälkeen. Sekvenssikaavion tilanteessa oletetaan, että tippuvia Puyoja voidaan siirtää sivusuunnassa oikealle.
![sekvenssikaavio](https://github.com/villekov1/otm-harjoitustyo/blob/master/dokumentointi/sekvenssikaavioMoveRight.png "Sekvenssikaavio")
