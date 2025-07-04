package mypackage

class CountApiV1ServletSuite extends munit.FunSuite:

  def testCount(expectedCount: Int): Unit =
    test(s"GET /api/v1/count == \${expectedCount}") {

      val obtained: Response =
        Request(
          "GET",
          "http://localhost:8080/api/v1/count",
          Map.empty,
          None
        )

      val expected: Response =
        Response(
          200,
          Map("Content-Type" -> "application/json;charset=UTF-8"),
          body = s"""|{
                |  "value": \${expectedCount}
                |}""".stripMargin
        )

      assertEquals(
        obtained = obtained,
        expected = expected
      )
    }

  testCount(1)
  testCount(2)
  testCount(3)
