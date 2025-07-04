package mypackage

import upickle.default.Reader
import upickle.default.Writer
import upickle.default.macroRW
import upickle.default.read
import upickle.default.write

case class Count(value: Int):
  def toJson(): String =
    write(this, indent = 2)

object Count:

  implicit val r: Reader[Count] = macroRW
  implicit val w: Writer[Count] = macroRW

  def fromJson(json: String): Count =
    read(json)
