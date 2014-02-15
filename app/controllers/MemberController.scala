package controllers

import play.api._
import play.api.mvc._
import com.sysiq.commerce.slick.config.Config.driver._
import com.sysiq.commerce.slick.config.Config.{ db => configDB }

import com.sysiq.commerce.slick.member.MemberMappings._

object MemberController extends Controller {

  def list = Action {
    val users = configDB withSession { implicit session =>
      Users.list
    }
    Ok(views.html.member.list("Users")(users))
  }

}