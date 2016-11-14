package com.qbyte.hotelapi.service

import java.util.Calendar

import com.typesafe.config.ConfigFactory

import scala.collection.JavaConversions._
import scala.collection._
import scala.collection.concurrent.TrieMap

/**
  * This rate limit implementation is provided state data from the
  * given properties file.
  */
class RateLimitImpl(propertiesFileName: String) extends RateLimit {

  val config = ConfigFactory.parseResources(propertiesFileName)
  val apikeyMap = config.entrySet.toList.map(
    entry => (entry.getKey, entry.getValue.unwrapped.toString.toInt)
  ).toMap

  val defaultRate = apikeyMap.getOrElse("GLOBAL", 10000)

  val suspendedPeriod = apikeyMap.getOrElse("SUSPENDED", 300000)

  val concurrentMap: concurrent.Map[String, Long] = new TrieMap

  override def acceptRequest(rawApikey: String): Boolean = {
    val apikey = if (apikeyMap.contains(rawApikey)) rawApikey else "GLOBAL"
    val rate = apikeyMap.getOrElse(apikey, defaultRate)

    val currentTime = Calendar.getInstance.getTime.getTime
    val nextAllowedTime = currentTime + rate
    val allowedTime = concurrentMap.putIfAbsent(apikey, nextAllowedTime)

    allowedTime match {
      case Some(time) =>
        if (time > currentTime) {
          concurrentMap.put(apikey, currentTime + suspendedPeriod)
          false
        } else {
          concurrentMap.replace(apikey, time, nextAllowedTime)
        }
      case None => true
    }
  }
}
