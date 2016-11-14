package com.qbyte.hotelapi.service

import akka.actor.{Actor, Props}
import akka.event.Logging
import com.qbyte.hotelapi.dao.HotelDAO
import com.qbyte.hotelapi.enums.PriceSort
import com.qbyte.hotelapi.service.HotelServiceActor.GetSortHotelsReq

/**
  * Actor implementation that handles a message to get hotels using a HotelDAO.
  */
object HotelServiceActor {

  case class GetSortHotelsReq(cityId: String, sort: String)

  def props(hotelDAO: HotelDAO): Props = Props(new HotelServiceActor(hotelDAO))
}

class HotelServiceActor(hotelDAO: HotelDAO) extends Actor {

  val log = Logging(context.system, this)

  override def receive = {
    case GetSortHotelsReq(cityId, sort) =>
      sender() ! hotelDAO.findHotelByCityId(cityId, PriceSort.withName(sort.toUpperCase))
    case msg => log.warning(s"Received unknown message: $msg")
  }
}
