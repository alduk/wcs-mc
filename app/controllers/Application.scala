package controllers

import play.api._
import play.api.mvc._
import catalog.CatalogEntryMappings._
import com.typesafe.slick.driver.db2.DB2Driver.simple._

case class CatalogEntryV(catalogEntry: Option[CatalogEntry], baseItem: Option[BaseItem], price: Option[ListPrice], parent: Option[CatalogEntry], children: List[CatalogEntry], offerPrices: List[(Offer,OfferPrice)])

object Application extends Controller {

  def index = Action {
    Ok(views.html.index("Your new application is ready."))
  }

  def catalogEntry(id: Int) = Action {
    import config.DB._
    val catEntry = db withSession { implicit session =>
      val e = CatalogEntries.filter(_.id === id).take(1)

      val baseItem = e.flatMap(_.baseItem).firstOption
      val price = e.flatMap(_.listPrice).firstOption
      val parent = e.flatMap(_.parent)
      val children = e.flatMap(_.children)
      println(parent.selectStatement)
      val offerPrices = e.flatMap(_.offerPrices).list
      CatalogEntryV(e.firstOption, baseItem, price, parent.firstOption, children.list, offerPrices)
    }
    Ok(views.html.catalogEntry("Catalog Entries")(catEntry))
  }

  def catalogEntries(size: Int, skip: Int, sku: Option[String]) = Action {
    import config.DB._
    val catEntries = db withSession { implicit session =>
      val q = CatalogEntries
      val r = (sku match {
        case Some(value) => q.filter(_.partNumber.like(s"%$value%"))
        case None => q
      }).drop(skip).take(size)
      println(r.selectStatement)
      r.list
    }
    Ok(views.html.catalogEntries("Catalog Entries")(catEntries))
  }

}