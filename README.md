hotels-akka-rest
================

In the spirit of agile development this is a minimal solution and has not
been run as a test driven development because this was not a requirement.

Launch the application using the WebServer App object, or sbt assembly and execute the jar.

The host name and port can be set as system properties HS_HOST and HS_PORT respectively to
override the host and port for jar execution.

## Usage

The API can be invoked using requests of this form:
    http://localhost:8080/hotel/TLA?sort=ASC
    http://localhost:8080/hotel/TLA?apikey=AB1234&sort=ASC
* where TLA is the 3 letter abbreviation to identify a city, i.e BNG=Bangkok

## Configuration Files

api-rate.properties contains the apikey values and associated rate limiting times
hoteldb.csv contains the hotel data
application.conf provides logging config