package controllers

import play.api._
import play.api.mvc._
import com.sysiq.commerce.slick.config.Config.driver._
import com.sysiq.commerce.slick.config.Config.{ db => configDB }

import com.sysiq.commerce.slick.catalog.CatalogMappings._
import com.sysiq.commerce.slick.catalog.CatalogEntryMappings._

case class CatalogGroupV(group: CatalogGroup, children: List[CatalogGroup], entries: List[CatalogEntry])

object CatalogGroupController extends Controller {

  def list(size: Int, skip: Int, sku: Option[String]) = Action {
    val catGroups = configDB withSession { implicit session =>
      CatalogGroups.list()
    }
    Ok(views.html.catalog.group.list("Catalog Groups")(catGroups))
  }

  def view(id: Long) = Action {
    val catGroup = configDB withSession { implicit session =>
      val q = CatalogGroups.filter(_.id === id).take(1)
      val children = q.flatMap(_.children)
      val entries = q.flatMap(_.entries)
      CatalogGroupV(q.first, children.list(), entries.list())
    }
    Ok(views.html.catalog.group.view("Catalog Group")(catGroup))
  }
}