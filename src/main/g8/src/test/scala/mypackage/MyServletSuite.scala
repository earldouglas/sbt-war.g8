package mypackage

import http.Request
import http.Response
import munit.FunSuite

class MyServletSuite extends FunSuite:

  test("GET /hello"):
    val expected = 
      Response(
        200,
        Map("Content-Type" -> "text/html;charset=UTF-8"),
        body = """|<html>
                  |  <body>
                  |    <h1>Hello, world!</h1>
                  |  </body>
                  |</html>""".stripMargin
      )

    val obtained =
      Request("GET", "http://localhost:8080/hello", Map.empty, None)

    assertEquals(
      expected = expected,
      obtained = obtained
    )
