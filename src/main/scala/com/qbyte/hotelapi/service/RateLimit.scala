package com.qbyte.hotelapi.service

/**
  * Objects of this type must implement the rate limiting behaviours.
  */
trait RateLimit {

  /** Limit the request rate based on the given api key value, or GLOBAL by default. */
  def acceptRequest(apikey: String = "GLOBAL"): Boolean
}