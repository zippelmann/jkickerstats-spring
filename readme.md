# Introduction
This app grabs all matches vom kickern-hamburg.de and makes it available as csv.

# The way it works
* grabs all seasons
* grabs all devisions from a season
* grabs all matches from a devision
* grabs all games from the matches
* repeat that for all seasons
* persists all matches and games
* provide a csv with all matches and games

# Setup
## prepare db
* nothing to do

## compile 
* clone this repo
* ./gradlew bootJar

## deployment
* copy the jar-file from the `build/libs`-directory to the server
* copy the file application.properties to the same directory
* customize the config to your setup 
* start the application with the following command: `java -Djava.awt.headless=true -server -Xms48m -Xmx250m -XX:MaxPermSize=150m -jar jkickerstats.jar`

# Dependencies
* java 1.8
* gradle
* jsoup
* tomcat
* spring boot

# My setup
* A shared hosting service
