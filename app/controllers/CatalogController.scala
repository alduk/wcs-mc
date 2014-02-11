package controllers

import play.api._
import play.api.mvc._
import com.sysiq.commerce.slick.config.Config.driver._
import com.sysiq.commerce.slick.config.Config.{ db => configDB }

import com.sysiq.commerce.slick.catalog.CatalogEntryMappings._
import com.sysiq.commerce.slick.catalog.CatalogMappings._

object CatalogController extends Controller {

  def list = Action {
    val catalogs = configDB withSession { implicit session =>
      Catalogs.list
    }
    Ok(views.html.catalog.list("Catalogs")(catalogs))
  }

}