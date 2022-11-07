### Oslo Bysykkel
Bare en enkel app basert på Bysykkel API, som gir en oversikt over sykkelstasjoner i Oslo med
stasjonsnavn, kapasitet, antall sykler og dokker tilgjengelig.
### Requirements:
- Java JDK
- Maven
- code editor (Visual Studio Code, IntelliJ, osv).
### Installasjon og Bruk:
- install Maven:\
  windows: https://byanr.com/installation-guides/maven-windows-11/ \
  macOs: https://www.digitalocean.com/community/tutorials/install-maven-mac-os
- last ned denne gitHub repo-en.
- unzip der du vil i filsystemet ditt.
- Naviger til pom.xml file i terminalen (eks:```~/Documents/API_oslo_komuna/maven/demo``` )
- kjør ````mvn install```` inn i samme mappe der pom filen er.
- åpne prosjektet i code editor-en du bruker (Visual studio code, IntelliJ)
- naviger til App.java \ (eks: ```~/Documents/API_oslo_komuna/maven/demo/src/main/java/com/bysykkelapp```)
- kjør appen ved å trykke på "play" button i editoren. \

![alt text](https://github.com/jovanDjordje/sykkelstasjoner-app/blob/master/table.png)

#### Ting å jobbe med:
-For øyeblikket hadde jeg bare tid til å skrive ut en liste til brukeren,
men dette kan forbedres ved å lage et mer passende brukergrensesnitt. \

-Jeg viser også kapasitetsfelt. Dette er fordi jeg synes det er fint
å ha det da det gir en slags sjekk da tilgjengelige sykler og docs
summerer opp til kapasitetsverdien. \

OBS: Dette er imidlertid ikke alltid tilfelle, og det kan være en feil i API
eller en feil i programmet mitt. Jeg gjorde noen manuelle tester av API,
og det ser ut som det er akkurat slik API er.

Jeg er også klar over asynkroniseringsalternativet lagt til i jdk 12,
men gitt omfanget av denne appen, synes jeg denne versjonen er bra.



