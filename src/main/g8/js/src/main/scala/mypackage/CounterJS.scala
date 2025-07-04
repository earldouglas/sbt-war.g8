package mypackage

import org.scalajs.dom.Element
import org.scalajs.dom.Event
import org.scalajs.dom.HTMLElement
import org.scalajs.dom.HTMLSpanElement
import org.scalajs.dom.Node
import org.scalajs.dom.XMLHttpRequest
import org.scalajs.dom.document
import scala.scalajs.js.annotation.JSExportTopLevel

object CounterJS:

  @JSExportTopLevel(name = "counter")
  def apply(): Node =

    val labelElement: HTMLSpanElement =
      document
        .createElement("span")
        .asInstanceOf[HTMLSpanElement]
    labelElement.className = "label"
    labelElement.textContent = "Count:"

    val countElement: Element = document.createElement("span")
    countElement.textContent = "0"

    val counterElement: Element = document.createElement("div")
    counterElement.appendChild(labelElement)
    counterElement.appendChild(countElement)

    val xhr = new XMLHttpRequest()
    xhr.open("GET", "/api/v1/count")
    xhr.onload = (e: Event) =>
      if xhr.status == 200 then
        val count: Count = Count.fromJson(xhr.responseText)
        countElement.textContent = count.value.toString()
    xhr.send()

    document
      .querySelector("body")
      .appendChild(counterElement)
