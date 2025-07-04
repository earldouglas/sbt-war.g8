package mypackage

import jakarta.servlet.annotation.WebServlet
import jakarta.servlet.http.HttpServlet
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import java.util.concurrent.atomic.AtomicReference

@WebServlet(urlPatterns = Array("/api/v1/count"))
class CountApiV1Servlet extends HttpServlet:

  private val count: AtomicReference[Int] =
    new AtomicReference(0)

  override def doGet(
      req: HttpServletRequest,
      res: HttpServletResponse
  ): Unit =

    count.getAndUpdate(_ + 1)

    res.setContentType("application/json")
    res.setCharacterEncoding("UTF-8")
    res.getWriter.write(Count(count.get()).toJson())
