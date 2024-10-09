package http

import java.net._
import scala.collection.JavaConverters._
import scala.io.Source

case class Response(
    status: Int,
    headers: Map[String, String],
    body: String
)

object Request:

  private def isOpen(port: Int): Boolean =
    try {
      import java.net.Socket
      import java.net.InetSocketAddress
      val socket: Socket = new Socket()
      socket.connect(new InetSocketAddress("localhost", port))
      socket.close()
      true
    } catch {
      case e: Exception => false
    }

  private def awaitOpen(port: Int, retries: Int): Unit =
    if (!isOpen(port)) {
      if (retries > 0) {
        Thread.sleep(100)
        awaitOpen(port, retries - 1)
      } else {
        throw new Exception(s"expected port $port to be open")
      }
    }

  def apply(
      method: String,
      urlString: String,
      headers: Map[String, String],
      body: Option[String]
  ): Response =

    val url: URL =
      new URL(urlString)

    awaitOpen(url.getPort(), 50)

    val c =
      url
        .openConnection()
        .asInstanceOf[HttpURLConnection]

    c.setInstanceFollowRedirects(false)
    c.setRequestMethod(method)
    c.setDoInput(true)
    c.setDoOutput(body.isDefined)

    headers foreach { case (k, v) =>
      c.setRequestProperty(k, v)
    }

    body foreach { b =>
      c.getOutputStream.write(b.getBytes("UTF-8"))
    }

    val response =
      Response(
        status = c.getResponseCode(),
        headers = c
          .getHeaderFields()
          .asScala
          .filter({ case (k, _) => k != null })
          .map({ case (k, v) => (k, v.asScala.mkString(",")) })
          .toMap - "Keep-Alive" - "Connection" - "Date" - "Content-Length",
        body = Source.fromInputStream {
          if (c.getResponseCode() < 400)
            c.getInputStream
          else
            c.getErrorStream
        }.mkString
      )

    c.disconnect()

    response
