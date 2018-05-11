# Vaatimusmäärittely (päivitetty vastaamaan lopullista versiota)

## Sovelluksen tarkoitus
Sovellus on pulmapeli, joka mukailee Puyo Puyo -nimistä peliä. Pelissä ruudukon katosta tippuu kahden värillisen pallon muodostamia kappaleita. Vähintään neljän samanvärisen kappaleen muodostama sarja katoaa kentältä ja antaa pelaajalle pisteitä.

## Käyttäjät
Sovellus ei sisällä erilaisia käyttäjiä, mutta huipputulokset nimetään 
käyttäjän nimen perusteella. Huipputulokset tallennetaan 
SQLite-tietokantaan.

## Käyttöliittymäluonnos
![alt text](https://github.com/villekov1/otm-harjoitustyo/blob/master/dokumentointi/kuvat/kayttoliittymaluonnos.jpg "Käyttöliittymän luonnos")

# Perusversion tarjoama toiminnallisuus
Pelissä kahden värikkään pallon (Puyon) muodostama kappale tippuu yksi 
kerrallaan ruudukon katosta. Peli sisältää viisi eri väriä; vihreä, 
punainen, keltainen, sininen ja violetti. Ilmassa olevia Puyoja pystyy 
kääntämään joko vasta- tai myötäpäivään akselinsa ympäri, mutta Puyon 
kohdattua maan tai toisen maassa olevan Puyon, se pysähtyy paikalleen. 
Ruudukko on standardikooltaan 6x13, mutta sen kokoa voi halutessaan säätää.

Mikäli pelaaja onnistuu muodostamaan vähintään neljän Puyon sarjan, 
kyseisen sarjan Puyot katoavat. Sarjassa olevien kappaleiden pitää olla 
joko vierekkäin tai päällekkäin toisiinsa nähden. Sarjan kadottua 
kadonneiden Puyojen yläpuolella olevat Puyot tippuvat alas, jolloin voi 
muodostua uusia sarjoja. Kaikki tämä tapahtuu saman ruudunpäivityksen 
aikana.

Peli päättyy, jos ylimmän rivin keskimmäinen paikka täyttyy. Tällöin 
kentälle ei nimittäin olisi mahdollista tulla uusia Puyoja, koska ne ilmestyvät 
aina kentän keskelle.

Perustoiminnallisuuksiin kuuluvat:
* Puyojen tippuminen, kääntö ja lukkiutuminen maahan
* Puyojen kasaantuminen päällekkäin
* Ketjujen tunnistaminen ja niiden tuhoaminen
* Pisteiden antaminen tuhotuista Puyoista
* Seuraavien Puyojen arpominen
* Huipputuloslista, joka tallentaa tulokset SQL-tietokantaan SQLiteä käyttäen
* Alkujaan jatkokehitysideana oli myös erikokoiset kentät, jotka myös toteutin
* Alkuperäiseen vaatimusmäärittelyyn nähden lisäsin pelinäkymään myös ominaisuuden, joka näyttää seuraavana vuorossa olevat Puyot. Tosin tämän olin piirtänyt myös käyttöliittymäluonnokseen.

# Jatkokehitysideoita
Jos sovellusta haluaa jatkokehittää, ainakin seuraavat ovat mahdollisia kehitysideoita:

* Jonkinlainen combo-systeemi, joka antaa enemmän pisteitä, jos useita 
ketjuja tuhoaa sarjassa.
* Moninpeli. Jos moninpelistä haluaa kiinnostavamman, ns. "roskapuyot" 
voisivat olla hyvä lisäys. Nämä palikat syntyvät toisen pelaajan tuhotessa omia puyojaan.
