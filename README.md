# Netlex

<img src="https://github.com/Eelii/netlex/blob/master/img/log_in.PNG" height=50% width=50%>
<img src="https://github.com/Eelii/netlex/blob/master/img/muokkaa.PNG" height=50% width=50%)>
<img src="https://github.com/Eelii/netlex/blob/master/img/saadokset.PNG" height=50% width=50%)>

Netlex on Java-kielinen, Spring-kehikolle perustuva Finlex-sivuston jäljitelmä. Ohjelman selainkäyttöliittymän kautta voidaan kirjautuneesta käyttäjästä riippuen tarkastella, luoda, muokata tai poistaa säädöksiä. Ohjelman tietokantaan voidaan lisätä säädöksiä selainkäyttöliittymän lisäksi API-rajapinnan kautta (kts. post-api.py). 

## Käytetyt teknologiat:

Kehikko: Spring <br>
Palvelin: Apache Tomcat
Tietokanta: H2 Database
Template engine: Thymeleaf

## post-api.py -skriptin käyttö

Uusi säädöksiä Netlex-ohjelmaan sen API-rajapinnan kautta voi lähettää post-api.py -skriptin avulla. Skripti suoritettaessa se hakee samassa juurikansiossa olevien, pelkällä vuosiluvulla nimettyjen kansioiden sisällöstä satunnaisen XML-tiedoston, jonka perusteella skripti luo säädös-olion ja lähettää sen POST-pyyntönä osoitteeseen http://localhost:8080. 
XML-tiedostot tulee erikseen ladata Semanttinen Finlex -palvelusta osoitteesta https://data.finlex.fi/download/xml/asd.html.
