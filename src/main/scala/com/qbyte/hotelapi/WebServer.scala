package com.qbyte.hotelapi

import akka.actor._
import akka.http.scaladsl.Http
import akka.stream.ActorMaterializer
import com.qbyte.hotelapi.dao.{CSVHotelDAO, HotelDAO}
import com.qbyte.hotelapi.rest.RestInterface
import com.qbyte.hotelapi.service.{HotelServiceActor, RateLimit, RateLimitImpl}

/**
  * Top level application class to launch the hotel api  REST service.
  */
object WebServer extends App with RestInterface {

  lazy val host = Option(System.getProperty("HS_HOST")).getOrElse("localhost")
  lazy val port = Option(System.getProperty("HS_PORT")).getOrElse("8080").toInt

  implicit val system = ActorSystem("hotel-service")
  implicit val materializer = ActorMaterializer()

  implicit val executionContext = system.dispatcher

  override lazy val rateLimit: RateLimit = new RateLimitImpl("api-rate.properties")

  val hotelDAO: HotelDAO = new CSVHotelDAO("/hoteldb.csv")
  override lazy val hotelService = system.actorOf(HotelServiceActor.props(hotelDAO), "hotel-service")

  Http().bindAndHandle(handler = routes, interface = host, port = port) map { binding =>
    println(s"REST interface bound to ${binding.localAddress}") } recover { case ex =>
    println(s"REST interface could not bind to $host:$port", ex.getMessage)
  }
}
