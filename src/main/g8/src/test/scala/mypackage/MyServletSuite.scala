package mypackage

import http.Request
import http.Response
import org.junit.Assert.assertEquals
import org.junit.Test

class MyServletSuite:

  @Test
  def getRoot(): Unit =
    assertEquals(
      Response(
        200,
        Map("Content-Type" -> "text/html;charset=UTF-8"),
        body = """|<html>
                       |  <body>
                       |    <h1>Hello, world!</h1>
                       |  </body>
                       |</html>""".stripMargin
      ),
      Request("GET", "http://localhost:8080/hello", Map.empty, None)
    )
