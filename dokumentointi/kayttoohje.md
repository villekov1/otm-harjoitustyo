# Käyttöohje

Sovelluksen suorittaminen on helpointa lataamalla sovelluksen 
GitHub-release, mutta voit myös generoida Jar-tiedoston komennolla *mvn package* sovelluksen juurikansiossa.

## Ohjelman käynnistäminen

Ohjelman käynnistäminen onnistuu komentorivikomennolla: *java -jar 
PuyoPuyoPeli.jar*. 
Ainakin Windowsilla voit myös tuplaklikata Jar-tiedostoa, jolloin se 
käynnistyy automaattisesti. Käynnistyksen yhteydessä ohjelma luo tietokannan huipputulokset.db.

## Sovelluksen käyttö

Sovellus on pulmapeli, jossa katosta tippuvia kahden värillisen pallon 
("Puyon") muodostamia kappaleita on järjestettävä sarjoiksi, jotka 
sisältävät vähintään neljä samanväristä Puyoa.

### Alkuvalikko
Pelin aloittamiseksi kirjoita nimesi tekstikenttään ja paina 
"Aloita"-näppäintä. Tyhjät tai pelkästään välilyöntejä 
sisältävät nimet eivät ole sallittuja. Tekstikentän vieressä olevilla 
slidereilla voit säätää kentän leveyttä ja korkeutta. 

Huipputulokset-näppäimellä pääset tarkastelemaan kaikkia huipputuloksia.

Pelin käyttöohjeet ovat esillä myös alkuvalikon alalaidassa olevassa 
tekstikentässä.

### Pelinäyttö
Voit siirtää tippuvia Puyoja sivusuunnassa nuolinäppäimillä. Voit 
kääntää Puyoa myötäpäivään joko Enter- tai S-näppäimellä, ja 
vastapäivään joko BackSpace- tai A-näppäimellä.

Lisäksi voit painaa alas-nuolinäppäintä, jolloin Puyot tippuvat 
nopeammin alaspäin (ns. "soft drop"). Painamalla ylös-nuolinäppäintä 
Puyot tippuvat välittömästi maahan (ns. "hard drop").

Kun tippuva Puyo kohtaa alimman rivin tai sen alapuolella on toinen 
Puyo, se lukkiutuu paikalleen. Tällöin et enää voi siirtää tai kääntää 
sitä. Jos muodostuu vähintään neljän samanvärisen Puyon sarja, se 
katoaa, jolloin saat pisteitä. Tämän jälkeen kadonneiden Puyojen yllä olevat Puyot tippuvat 
alas, jolloin voi edelleen muodostua uusia ketjuja.

Pelin tavoitteena on saada mahdollisimman paljon pisteitä. Pisteiden 
kertyessä Puyot lisäksi tippuvat yhä nopeammin alas. Mikäli ylimmän 
rivin keskimmäinen paikka täyttyy, peli päättyy, jolloin pelaaja 
palautetaan alkuvalikkoon ja tulos tallennetaan tietokantaan.

Ruudun oikeassa yläkulmassa näkyvät seuraavana vuorossa olevat Puyot. Pelinäkymän yläpalkin napeista voit joko pysäyttää pelin, aloittaa sen alusta tai palata alkuvalikkoon. Voit pysäyttää pelin tai jatkaa sitä myös P-näppäintä painamalla.

### Huipputulosnäkymä
Huipputulosnäkymässä voit järjestää huipputulokset joko nimen tai 
pisteiden perusteella vasemmalla sivulla olevista näppäimistä. Voit 
lisäksi poistaa tuloksen tietokannasta klikkaamalla tulosta ja sen 
jälkeen "Poista"-näppäintä. Alkuvalikkoon palaaminen on helppoa 
yläpalkin näppäimestä.

Huomaa, että nimellä järjestettäessä isolla kirjaimella kirjoitetut 
nimet ovat aina ensimmäisenä.
