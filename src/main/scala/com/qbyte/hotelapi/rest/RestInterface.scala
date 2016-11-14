package com.qbyte.hotelapi.rest

import akka.actor.ActorRef
import akka.http.scaladsl.server.{Directives, Route}
import akka.pattern.ask
import akka.util.Timeout
import com.qbyte.hotelapi.entities.Hotel
import com.qbyte.hotelapi.rest.serializers.HotelJsonSupport
import com.qbyte.hotelapi.service.HotelServiceActor.GetSortHotelsReq

import scala.concurrent.duration._

/**
  * Implement the hotel entity REST service. This service will default to the
  * GLOBAL apikey and ASC price order.
  */
trait RestInterface extends Directives with RateLimitDirectives with HotelJsonSupport {

  implicit val timeout = Timeout(10 seconds)

  val hotelService: ActorRef

  val routes: Route = pathPrefix("hotel") {
    path(Segment) { cityId =>
      get {
        parameter('apikey ? "GLOBAL", 'sort ? "ASC") { (apikey, sort) =>
          rateLimiter(apikey) {
            complete((hotelService ? GetSortHotelsReq(cityId, sort)).mapTo[List[Hotel]])
          }
        }
      }
    }
  }
}
