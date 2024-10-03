package mypackage

import jakarta.servlet.annotation.WebServlet
import jakarta.servlet.http.HttpServlet
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse

@WebServlet(urlPatterns = Array("/hello"))
class Servlet extends HttpServlet:
  override def doGet(
      req: HttpServletRequest,
      res: HttpServletResponse
  ): Unit =
    res.setContentType("text/html")
    res.setCharacterEncoding("UTF-8")
    val responseBody: String =
      """<html>
        |  <body>
        |    <h1>Hello, world!</h1>
        |  </body>
        |</html>""".stripMargin
    res.getWriter.write(responseBody)
