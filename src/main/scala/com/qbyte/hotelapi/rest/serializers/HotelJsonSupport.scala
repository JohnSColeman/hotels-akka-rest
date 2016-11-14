package com.qbyte.hotelapi.rest.serializers

import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport
import com.qbyte.hotelapi.entities.Hotel
import spray.json._

/** Performs JSON marshalling. */
trait HotelJsonSupport extends SprayJsonSupport with DefaultJsonProtocol {

  implicit val hotelFormat = jsonFormat5(Hotel) // Hotel has 5 properties
}
