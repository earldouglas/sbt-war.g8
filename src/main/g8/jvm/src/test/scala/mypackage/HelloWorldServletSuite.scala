package mypackage

class HelloWorldServletSuite extends munit.FunSuite:

  test("GET /hello.html") {

    val obtained: Response =
      Request(
        "GET",
        "http://localhost:8080/hello.html",
        Map.empty,
        None
      )

    val expected: Response =
      Response(
        200,
        Map("Content-Type" -> "text/html;charset=UTF-8"),
        body = """|<html>
             |  <body>
             |    <h1>Hello, world!</h1>
             |  </body>
             |</html>""".stripMargin
      )

    assertEquals(
      obtained = obtained,
      expected = expected
    )
  }
