package com.qbyte.hotelapi.dao

import com.qbyte.hotelapi.entities.Hotel
import com.qbyte.hotelapi.enums.PriceSort
import com.qbyte.hotelapi.enums.PriceSort.PriceSort
import com.qbyte.hotelapi.utils.Helper

/**
  * This implementation obtains its state data from a CSV file with the given name.
  */
class CSVHotelDAO(csvFileName: String) extends HotelDAO {

  var hotels = collection.mutable.HashMap[String, collection.mutable.TreeSet[Hotel]]()

  val bufferedSource = io.Source.fromInputStream(getClass.getResourceAsStream(csvFileName))

  implicit val hotelOrdering: Ordering[Hotel] = Ordering.by(_.price)

  for (line <- bufferedSource.getLines) {
    val cols = line.split(",").map(_.trim)
    val cityId = Helper.getThreeLetterAbreviation(cols(0))
    val hotel = Hotel(cityId, cols(0), cols(1).toInt, cols(2), cols(3).toInt)

    var hotelList = hotels.getOrElse(cityId, new collection.mutable.TreeSet[Hotel])
    hotelList += hotel
    hotels += (cityId -> hotelList)
  }
  bufferedSource.close

  val hotelsMap = hotels.map(kv => (kv._1, kv._2.toList)).toMap

  override def findHotelByCityId(cityId: String, sort: PriceSort): List[Hotel] = {
    val hotelsAsc = hotelsMap.getOrElse(cityId, List())
    if (sort == PriceSort.ASC) {
      hotelsAsc
    } else {
      hotelsAsc.reverse
    }
  }

}
