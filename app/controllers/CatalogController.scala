package controllers

import play.api._
import play.api.mvc._
import com.sysiq.commerce.slick.config.Config.driver._
import com.sysiq.commerce.slick.config.Config.{ db => configDB }

import com.sysiq.commerce.slick.catalog.CatalogMappings._

import play.api.libs.json._

object CatalogController extends Controller {

  implicit object CatalogsW extends Writes[Catalog] {
    def writes(c: Catalog) = Json.obj(
      "id" -> Json.toJson(c.id),
      "memberId" -> Json.toJson(c.memberId.toString()),
      "identifier" -> Json.toJson(c.identifier),
      "description" -> Json.toJson(c.description))
  }
  def list = Action {
    val catalogs = configDB withSession { implicit session =>
      Catalogs.list
    }
    Ok(views.html.catalog.list("Catalogs")(catalogs))

  }

  def listJson = Action {
    val catalogs = configDB withSession { implicit session =>
      Catalogs.list
    }
    Ok(Json.toJson(catalogs))
  }
}