package controllers

import play.api._
import play.api.mvc._
import catalog.CatalogEntryMappings._
import com.typesafe.slick.driver.db2.DB2Driver.simple._

object Application extends Controller {

  def index = Action {
    Ok(views.html.index("Your new application is ready."))
  }

  def catalogEntry(id: Int) = Action {
    import config.DB._
    val catEntry = db withSession { implicit session =>
      val e = CatalogEntries.filter(_.id === id).take(1)

      val b = e.flatMap(_.baseItem).firstOption
      val p = e.flatMap(_.listPrice).firstOption

      val q = for {
        e1 <- CatalogEntries.filter(_.id === id)
        b1 <- e1.baseItem
        p1 <- e1.listPrice
      } yield (e1, b1, p1)
      println(q.selectStatement)
      println(q.list)
      println(q.list.length)
      (e.firstOption, b, p)
    }
    Ok(views.html.catalogEntry("Catalog Entries")(catEntry))
  }

  def catalogEntries(size: Int, skip: Int) = Action {
    import config.DB._
    val catEntries = db withSession { implicit session =>
      CatalogEntries.drop(skip).take(size).list
    }
    Ok(views.html.catalogEntries("Catalog Entries")(catEntries))
  }

}