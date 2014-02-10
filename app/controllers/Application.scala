package controllers

import play.api._
import play.api.mvc._
import catalog.CatalogEntryMappings._
import config.Config.driver._
import config.Config.{ db => configDB }

object Application extends Controller {

  def index = Action {
    Ok(views.html.index("Your new application is ready."))
  }
}