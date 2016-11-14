package com.qbyte.hotelapi.rest

import akka.http.scaladsl.model.StatusCodes.TooManyRequests
import akka.http.scaladsl.server.Route
import akka.http.scaladsl.server.directives.RouteDirectives
import com.qbyte.hotelapi.service.RateLimit

/**
  * Implements rate limiting for a route using the given apikey and route.
  */
trait RateLimitDirectives extends RouteDirectives {

  val rateLimit: RateLimit

  def rateLimiter(apikey: String)(r: => Route): Route = {
    if (rateLimit.acceptRequest(apikey)) {
      r
    } else {
      complete(TooManyRequests, TooManyRequests.reason)
    }
  }
}
