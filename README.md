# Netlex

<img src="https://github.com/Eelii/netlex/blob/master/img/log_in.PNG" height=50% width=50%>
<img src="https://github.com/Eelii/netlex/blob/master/img/muokkaa.PNG" height=50% width=50%)>
<img src="https://github.com/Eelii/netlex/blob/master/img/saadokset.PNG" height=50% width=50%)>

Netlex on Java-kielinen, Spring-kehikolle perustuva Finlex-sivuston jäljitelmä. Ohjelman selainkäyttöliittymän kautta voidaan kirjautuneesta käyttäjästä riippuen tarkastella, luoda, muokata tai poistaa säädöksiä. Ohjelman tietokantaan voidaan lisätä säädöksiä selainkäyttöliittymän lisäksi REST-rajapinnan kautta (kts. post-api.py). 

## Käytetyt teknologiat:

Kehikko: Spring <br>
Palvelin: Apache Tomcat <br>
Tietokanta: H2 Database <br>
Template engine: Thymeleaf <br>
3D-animaatio: Babylon.js

## post-api.py -skriptin käyttö

Uusi säädöksiä Netlex-ohjelmaan sen REST-rajapinnan kautta voi lähettää post-api.py -skriptin avulla. Skripti suoritettaessa se hakee samassa juurikansiossa olevien, pelkällä vuosiluvulla nimettyjen kansioiden sisällöstä satunnaisen XML-tiedoston, jonka perusteella skripti luo säädös-olion ja lähettää sen POST-pyyntönä osoitteeseen http:<nohyperlink/>//localhost:8080. <br>
XML-tiedostot tulee erikseen ladata Semanttinen Finlex -palvelusta osoitteesta https://data.finlex.fi/download/xml/asd.html.
