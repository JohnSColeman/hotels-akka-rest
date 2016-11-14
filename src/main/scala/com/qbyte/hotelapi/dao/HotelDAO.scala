package com.qbyte.hotelapi.dao

import com.qbyte.hotelapi.entities.Hotel
import com.qbyte.hotelapi.enums.PriceSort.PriceSort

/**
  * Objects of this type will implement data access behaviours for the Hotel entity.
  */
trait HotelDAO {
  def findHotelByCityId(cityId: String, sort: PriceSort): List[Hotel]
}

