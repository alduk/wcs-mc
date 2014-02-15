package util

import play.api.templates.BufferedContent
import play.api.http.MimeTypes

class Ang(buffer: StringBuilder) extends BufferedContent[Ang](buffer) {
  /**
   * Content type of HTML.
   */
  val contentType = MimeTypes.HTML
}

/**
 * Helper for HTML utility methods.
 */
object Ang {

  /**
   * Creates an HTML fragment with initial content specified.
   */
  def apply(text: String): Ang = {
    new Ang(new StringBuilder("{{" + text + "}}"))
  }

  /**
   * Creates an empty HTML fragment.
   */
  def empty: Ang = new Ang(new StringBuilder)
}