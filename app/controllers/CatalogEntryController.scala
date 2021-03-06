package controllers

import play.api._
import play.api.mvc._
import com.sysiq.commerce.slick.config.Config.driver._
import com.sysiq.commerce.slick.config.Config.{ db => configDB }

import com.sysiq.commerce.slick.catalog.CatalogEntryMappings._
import com.sysiq.commerce.slick.catalog.CatalogMappings._

case class CatalogEntryV(catalogEntry: Option[CatalogEntry], baseItem: Option[BaseItem], price: Option[ListPrice], parent: Option[CatalogEntry], children: List[CatalogEntry], offerPrices: List[(Offer, OfferPrice)])

object CatalogEntryController extends Controller {

  def view(id: Long) = Action {
    val catEntry = configDB withSession { implicit session =>
      val e = CatalogEntries.filter(_.id === id).take(1)

      val baseItem = e.flatMap(_.baseItem).firstOption
      val price = e.flatMap(_.listPrice).firstOption
      val parent = e.flatMap(_.parent)
      val children = e.flatMap(_.children)
      println(parent.selectStatement)
      val offerPrices = e.flatMap(_.offerPrices).list
      CatalogEntryV(e.firstOption, baseItem, price, parent.firstOption, children.list, offerPrices)
    }
    Ok(views.html.catalog.entry.view("Catalog Entries")(catEntry))
  }

  def list(size: Int, skip: Int, sku: Option[String]) = Action {
    val catEntries = configDB withSession { implicit session =>
      val q = CatalogEntries
      val r = (sku match {
        case Some(value) => q.filter(_.partNumber.like(s"%$value%"))
        case None => q
      }).drop(skip).take(size)
      println(r.selectStatement)
      r.list
    }
    Ok(views.html.catalog.entry.list("Catalog Entries")(catEntries))
  }
}