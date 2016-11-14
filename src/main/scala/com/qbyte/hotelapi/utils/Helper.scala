package com.qbyte.hotelapi.utils

/** Implements helper methods. */
object Helper {

  /** Provides an abbreviated form of the given string.
    * Used to reduce and regulate URL length. */
  def getThreeLetterAbreviation(name: String): String = {
    var id = name.charAt(0).toString
    for (ch <- name.toUpperCase.substring(1); if !"AEIOU".contains(ch) && id.length < 3) id += ch
    id
  }
}