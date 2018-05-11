# Testausdokumentti
Ohjelmaa on testattu JUnitilla yksikkö- ja integraatiotestein, minkä 
lisäksi olen testannut sovelluksen toimintaa laajasti käytännön testein. 

## Yksikkö- ja integraatiotestaus
Sovelluslogiikkaa on testattu laajasti JUnit-testeillä, mutta 
käyttöliittymästä vastaavaa luokkaa PuyoPuyoUi ei ole testattu. Lisäksi 
enum-tyyppinen Colour-luokka on testaamatta.

Integraatiotestaus ja yksikkötestaus on toteutettu samojen luokkien
alla, koska integraatiotestaus ei tässä tapauksessa oikeastaan eroa 
yksikkötestauksesta. GameLogic-luokka sisältää FilledMap- ja 
Puyo-luokkien olentoja, joten niiden tekemä yhteistyö tulee testattua 
metodien toimintaa testaamalla. Vastaavasti Puyo-luokan oliot sisältävät 
Colour-tyypin muuttujan.

ScoreDaoTest-luokka hyödyntää testaamiseen tarkoitettua tietokantaa. 
Tämä tietokanta tyhjennetään jokaisen testauksen lopuksi, ja se luodaan 
automaattisesti, jos sitä ei ole olemassa.

### Testauskattavuus
Sovelluksen testien kattavuutta on mitattu Jacocon avulla. Alla on kuva Jacoco-testikattavuusraportista. Kattavuusraportissa ei ole otettu käyttöliittymästä vastaavaa luokkaa huomioon.
![alt text](https://github.com/villekov1/otm-harjoitustyo/blob/master/dokumentointi/kuvat/jacocotestikattavuus.JPG "Jacoco-testikattavuusraportti")

Kuten kuvasta nähdään, testien rivikattavuus on erittäin hyvä 99%. 
Puuttuva prosentti johtuu käsittääkseni siitä, että Jacoco ei osaa 
tulkita break-komentoa mukaan. Myös haaraumakattavuus on melko hyvä 80%, 
mutta siinä on jonkin verran paranneltavaa.
 
## Järjestelmätestaus
Sovellusta on testattu manuaalisesti sekä Windows 10- että 
Linux-ympäristössä. Huomionarvoista on, että koneella täytyy olla 
asennettuna Javan ohella myös SQLite.

## Sovellukseen jääneet ongelmat
- Olen kohdannut muutaman kerran melko epätavallisen bugin, jossa tippuva 
Puyo menee toisen Puyon läpi. En kuitenkaan löytänyt ongelman syytä. Toisaalta en ole 
myöskään saanut sitä tuotettua keinotekoisesti, joten ongelma ei ole 
suuri.
- ScoreDao -luokassa on hieman toisteista koodia.
- Ohjelmassa on kolme Checkstyle-virhettä, mutta ne ovat melko lieviä metodien pituuteen liittyviä virheitä. Kyseisten metodien jakaminen useaan osaan olisi mielestäni tehnyt niistä epäselvempiä.
- Käyttöliittymä on kokonaisuudessaan yhden luokan alla, mikä näkyy 
luokan pituudessa.
