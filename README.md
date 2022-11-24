## Problem Statement
Process log file and return the most frequent cookie for a specific day.
The most active cookie defined as one seen in the log the most times during a given day.

## Technologies used in coding task
    Java 11
    Spring Boot
    Maven

## How to run code

Use maven to build jar and run the compiled jar file by below commands:     
~~~
mvn clean package
java -jar target\cookie-filter-version-SNAPSHOT.jar -f csv-file-path -d selected-date
~~~

- Example:
Your program to return the most active cookie for 9th DEc 2018-12-09
~~~
java -jar target\cookie-filter-0.0.1-SNAPSHOT.jar -f src\logs\cookie_log.csv -d 2018-12-09
~~~