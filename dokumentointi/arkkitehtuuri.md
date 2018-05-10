# Arkkitehtuurikuvaus

## Rakenne
Sovellus noudattaa karkeasti seuraavanlaista pakkaushierarkiaa. Tarkempi kuvaaja löytyy alla olevasta pakkauskaaviosta.
![pakkaushierarkia](https://github.com/villekov1/otm-harjoitustyo/blob/master/dokumentointi/kuvat/Pakkaushierarkia.jpg "Pakkaushierarkia")

Pakkaus *PuyoPuyoPeli.ui* vastaa sovelluksen graafisesta käyttöliittymästä. Pakkaus *PuyoPuyoPeli.domain* puolestaan sisältää sovelluslogiikkaan ja sovelluksen toiminnan kannalta keskeisiin käsitteisiin liittyvät luokat. Pakkaus *PuyoPuyoPeli.database* sisältää tietokantatoiminnallisuuteen liittyvät luokat.

## Käyttöliittymä
PuyoPuyoUi-luokka vastaa käyttöliittymän toteutuksesta. Käyttöliittymä 
sisältää kolme eri näkymää:
- Aloitusnäkymä (startView)
- Pelinäkymä (gameView)
- Huipputulosnäkymä (highScoreView)

Näkymät on toteutettu JavaFX:n Scene-luokkina. Näkymät sisältävät 
useita erilaisia komponentteja, kuten nappeja, tekstikenttiä, slidereita 
sekä Canvas-piirtoalustoja. Kerrallaan vain yksi näkymä on käytössä 
sen ollessa asetettuna Stage-ikkunan näkymäksi. 

Käyttöliittymä ei sisällä lainkaan sovelluslogiikkaa. Sen sijaan 
käyttöliittymän toiminnot, kuten pelinäkymän piirtäminen tai napin 
painallus kutsuvat sovelluslogiikasta vastaavan GameLogic-luokan 
metodeja.

## Sovelluslogiikka

Sovelluksen luokat ovat yhteydessä toisiinsa seuraavan luokkakaavion 
mukaisesti:
### Sovelluksen luokkakaavio:
![luokkakaavio](https://github.com/villekov1/otm-harjoitustyo/blob/master/dokumentointi/kuvat/UML_luokkakaavio.jpg "Luokkakaavio")

### Pakkauskaavio:
Alla olevassa pakkauskaaviossa on esitetty ylläolevan luokkakaavion 
luokat pakkauksien sisällä.
![pakkauskaavio](https://github.com/villekov1/otm-harjoitustyo/blob/master/dokumentointi/kuvat/Pakkauskaavio.jpg "Pakkauskaavio")

### Netbeansin kautta otettu kuva pakkausrakenteesta:
![pakkauskuva](https://github.com/villekov1/otm-harjoitustyo/blob/master/dokumentointi/kuvat/Pakkauskuva.JPG "Sovelluksen pakkausrakenne")

### Sovelluksen päätoiminnallisuudet
Luokka GameLogic vastaa pelilogiikasta. GameLogic-luokan keskiössä on 
lista Puyo-luokan alkioita, jotka sisältävät tiedon Puyon sijainnista 
sekä väristä. Tippuvat Puyot ovat erityisessä roolissa, sillä ne ovat 
ainoat Puyot, joita pelaaja voi suoranaisesti ohjata liikuttamalla 
niitä sivulle tai kääntämällä niitä myötä- tai vastapäivään.

Lisäksi erityisen tärkeää pelilogiikan kannalta on 
GameLogicin FilledMap-olio, joka sisältää tiedon siitä, mitkä 
peliruudukon paikat ovat täynnä. Tippuvien Puyojen viemiä paikkoja ei 
lasketa täysiksi, joten FilledMap sisältää myös tarvittavat tiedot sen 
määrittämiseksi, ovatko tippuvat Puyot maassa. FilledMap-luokan 
isTheSpaceFilled-metodin avulla on mahdollista selvittää, onko joku 
tietty ruutu täynnä.

Koko pelin toiminta perustuu GameLogic-luokan update-metodiin, joka 
tiputtaa tippuvia Puyoja alaspäin. Tämän jälkeen metodi tarkistaa 
findChain-metodin avulla, onko kentälle muodostunut samanväristen 
Puyojen muodostamia ketjuja. Mikäli on muodostunut ketjuja, jotka 
sisältävät vähintään neljä samanväristä Puyoa, kyseinen ketju tuhoutuu 
destroyChain-metodin avulla. Tämän jälkeen update kutsuu 
dropAll-metodia, joka tiputtaa kaikki mahdollisesti ilmaan jääneet Puyot 
maan tasolle. Tällöin saattaa myös syntyä uusia ketjuja, jolloin myös ne 
tuhotaan. Update-metodin toiminta päättyy, kun kaikki Puyot ovat maassa eikä uusia ketjuja ole syntynyt, 
jolloin asetetaan uudet tippuvat Puyot setPair- ja randomPuyo-metodien avulla.

Kuten yllä on mainittu, PuyoPuyoUi vastaa graafisen käyttöliittymän 
piirtämisestä. Tarkastellaan pelinäkymän toimintaa hieman 
tarkemmin. Jokaisen piirtosyklin aikana käyttöliittymä hakee 
getPuyos-metodin avulla pelilogiikan sisältämän listan Puyoja sekä 
piirtää ne näytölle. Lisäksi PuyoPuyoUi kutsuu GameLogic-olion update-metodia, jolloin 
GameLogic-olio *situation* sekä sen sisältämä FilledMap päivittyvät.

Huipputulosnäkymän kannalta erityisen oleellista on sovelluksen 
tietokantatoiminnallisuudet. Tietokantaan liittyviä luokkia ovat luokat 
Database ja ScoreDao. Database sisältää tietokannan osoitteen 
sekä mahdollisuuden yhteyden avaamiseen. Score-luokka sisältää tiedon 
pelaajan nimestä sekä tuloksesta. ScoreDaon avulla on mahdollista 
tallentaa tietokantaan pelaajan saavuttamat pisteet Score-luokan 
välityksellä sekä etsiä tietokannasta tulokset ja tulkita ne edelleen
Score-alkioiksi. Huipputulosnäkymän kautta on mahdollista tarkastella ja järjestää 
tuloksia sekä myös poistaa niitä.

## Tietojen tallennus
Tietojen tallennus tapahtuu SQLiten avulla SQL-tietokantaan. Sovellus 
luo käynnistyksen yhteydessä huipputulokset.db -tietokannan, jos 
sellaista ei vielä ole olemassa. Jos sellainen on olemassa, sovellus 
tarkistaa, että siellä on tietokantataulu Tulos sekä tarvittaessa luo 
sellaisen.

Tulos-taulu sisältää seuraavat tiedot: id (pääavain), nimi (String) ja 
tulos (int).

ScoreDaon avulla Score-luokan alkiot on mahdollista tallentaa 
tietokantaan, ja vastaavasti tietokannan sisältö on mahdollista tulkita 
Score-luokan alkioksi. Ainoastaan PuyoPuyoUi-luokka kutsuu ScoreDaon metodeja. GameLogic-luokka ei tee näillä tiedoilla mitään, mutta käyttöliittymän vastuulla on niiden tulostaminen sekä niiden poistaminen.

## Lisää sovelluksen toiminnasta

### Sekvenssikaavio tilanteesta, jossa käyttäjä siirtää tippuvia Puyoja oikealle
Alla olevassa kuvassa luokan sisäiset metodikutsut on esitetty väliaikaisina palkkeina, jotka tuhotaan suorituksen jälkeen. Sekvenssikaavion tilanteessa oletetaan, että tippuvia Puyoja voidaan siirtää sivusuunnassa oikealle.
![sekvenssikaavio](https://github.com/villekov1/otm-harjoitustyo/blob/master/dokumentointi/kuvat/sekvenssikaavioMoveRight.png "Sekvenssikaavio")

