## <img src="project-misc/check-logo.svg" width="80" height="80">  Avrela ![CI](https://github.com/lfx1001/avrela/actions/workflows/ci-pipeline.yml/badge.svg) [![Quality Gate Status](https://sonarcloud.io/api/project_badges/measure?project=lfx1001_avrela&metric=alert_status)](https://sonarcloud.io/summary/new_code?id=lfx1001_avrela) [![Maintainability Rating](https://sonarcloud.io/api/project_badges/measure?project=lfx1001_avrela&metric=sqale_rating)](https://sonarcloud.io/summary/new_code?id=lfx1001_avrela) [![Coverage](https://sonarcloud.io/api/project_badges/measure?project=lfx1001_avrela&metric=coverage)](https://sonarcloud.io/summary/new_code?id=lfx1001_avrela)

Automated virtual repository lab assesment.

### Build and run

#### Prerequisites

- Java 17
- Maven 3.0+

#### Run from the sources

Go on the project's root folder, then type:

    $ mvn spring-boot:run 

#### Build a packaged jar

    $ mvn package 

#### Run from a packaged jar

Go on the packaged jar folder, then type:

    $ java - jar <application>.jar 

#### Build a packaged war

Change the pom.xml packaging section from *jar* to *war*.

    $ mvn package 

#### Environment variables

- GITHUB_TOKEN : Use a GitHub token to avoid anoymous usage rate limits

### Usage

- Launch the application and go on http://localhost:8080/


