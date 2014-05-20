package controllers

import play.api._
import play.api.mvc._
import com.sysiq.commerce.slick.config.Config.driver._
import com.sysiq.commerce.slick.config.Config.{ db => configDB }

import com.sysiq.commerce.slick.pagelayouts.PageLayoutsMappings._

object PageLayoutController extends Controller {

  def layouts = Action {
    val res = configDB withSession { implicit session =>
      PageLayouts.list
    }
    Ok(views.html.pagelayouts.layouts("Page layouts")(res))
  }

  def view(layoutId: Long) = Action {
    val res = configDB withSession { implicit session =>
      PageLayouts.filter(_.id === layoutId).list
    }
    Ok(views.html.pagelayouts.layouts("Page layouts")(res))
  }

  def widgets = Action {
    val res = configDB withSession { implicit session =>
      Widgets.list
    }
    Ok(views.html.pagelayouts.widgets("Page layout widgets")(res))
  }

  def widgetsById(layoutId: Long) = Action {
    val res = configDB withSession { implicit session =>
      Widgets.filter(_.layoutId === layoutId).list
    }
    Ok(views.html.pagelayouts.widgets("Page layout widgets")(res))
  }

}