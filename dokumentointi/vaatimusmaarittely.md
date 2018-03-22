# Vaatimusmäärittely

## Sovelluksen tarkoitus
Sovellus on pulmapeli, joka mukailee Puyo Puyo -nimistä peliä. Pelissä ruudukon katosta tippuu kahden värillisen pallon muodostamia kappaleita. Vähintään neljän samanvärisen kappaleen muodostama sarja katoaa kentältä ja antaa pelaajalle pisteitä.

## Käyttäjät
Sovellus ei sisällä erilaisia käyttäjiä, mutta huipputulokset voi nimetä käyttäjän nimen perusteella.

## Käyttöliittymäluonnos


# Perusversion tarjoama toiminnallisuus
Pelissä kahden värikkään pallon (Puyon) muodostama kappale tippuu yksi 
kerrallaan ruudukon katosta. Peli sisältää viisi eri väriä; vihreä, 
punainen, keltainen, sininen ja violetti. Ilmassa olevaa palikkaa pystyy 
kääntämään joko vasta- tai myötäpäivään, mutta palikan kohdattua maan 
tai toisen maassa olevan palikan, se pysähtyy paikalleen. Ruudukko on 
standardikooltaan 6x13, mutta sitä voi mahdollisesti säätää.

Mikäli pelaaja onnistuu muodostamaan vähintään neljän palikan sarjan, 
kyseisen sarjan palikat katoavat. Sarjassa olevien palikoiden pitää olla 
joko vierekkäin tai päällekkäin toisiinsa nähden.

Peli päättyy, jos ylimmän rivin kolmas paikka täyttyy. Tällöin kentälle 
ei nimittäin olisi mahdollista tulla uusia Puyoja, koska ne ilmestyvät 
aina kentän keskelle.

Perustoiminnallisuuksiin kuuluvat:
  *Puyojen tippuminen, kääntö ja lukkiutuminen maahan
-Puyojen kasaantuminen päällekkäin
-Ketjujen tunnistaminen ja niiden tuhoaminen
-Pisteiden antaminen tuhotuista Puyoista
-Seuraavien Puyojen arpominen
-Huipputuloslista, joka tallentaa tulokset SQL-tietokantaan SQLiteä 
käyttäen


# Jatkokehitysideoita
Jos aikaa jää tai sovellusta haluaa jatkokehittää, ainakin seuraavat 
ovat mahdollisia kehitysideoita:
-Jonkinlainen combo-systeemi, joka antaa enemmän pisteitä, jos useita 
ketjuja tuhoaa sarjassa.
-Moninpeli. Jos moninpelin tekee, ns. "roskapuyot" voivat tehdä 
moninpelistä kiinnostavamman. Nämä palikat syntyvät toisen pelaajan 
tuhotessa omia puyojaan.
-Erikokoiset kentät.
