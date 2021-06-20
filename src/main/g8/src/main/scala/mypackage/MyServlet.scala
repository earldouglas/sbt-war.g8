package mypackage

import javax.servlet.annotation.WebServlet
import javax.servlet.http.HttpServlet
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@WebServlet(urlPatterns = Array("/hello"))
class Servlet extends HttpServlet:
  override def doGet(req: HttpServletRequest, res: HttpServletResponse): Unit =
    res.setContentType("text/html")
    res.setCharacterEncoding("UTF-8")
    val responseBody: String =
      """<html>
        |  <body>
        |    <h1>Hello, world!</h1>
        |  </body>
        |</html>""".stripMargin
    res.getWriter.write(responseBody)
