package controllers

import play.api._
import play.api.mvc._
import com.sysiq.commerce.slick.config.Config.driver._
import com.sysiq.commerce.slick.config.Config.{ db => configDB }

import com.sysiq.commerce.slick.accesscontrol.ACPoliciyMappings._
import com.sysiq.commerce.slick.commands.CommandMapings._

case class AcActionV(policy: ACPolicy, action: ACAction)
case class AcResourceV(policy: ACPolicy, resourceCategory: ACResourceCategory, command: Command)

object ACPController extends Controller {

  def actions(actionName: Option[String]) = Action {
    val actions = configDB withSession { implicit session =>
      val q = for {
        p <- ACPolicies
        rel <- ACActionGroupsRel if rel.actionGroupId === p.actionGroupId
        a <- ACActions if a.id === rel.actionId
      } yield (p, a)
      val qf = actionName match {
        case Some(value) => q.filter(_._2.action.like(s"%$value%"))
        case None => q
      }
      println(qf.selectStatement)
      qf.list.map(x => AcActionV(x._1, x._2))
    }
    Ok(views.html.acp.actions("ACP Actions")(actions))
  }

  def resources(className: Option[String]) = Action {
    val resources = configDB withSession { implicit session =>
      val q = for {
        p <- ACPolicies
        rel <- ACResourceGroupsRel if rel.resourceGroupId === p.resourseGroupId
        r <- ACResourceCategories if r.id === rel.resourceCategoryId
        c <- CommandRegistry if r.resourceClassName === c.interfaceName
      } yield (p, r, c)
      val qf = className match {
        case Some(value) => q.filter(_._3.className.like(s"%$value%"))
        case None => q
      }
      println(qf.selectStatement)
      qf.list.map(x => AcResourceV(x._1, x._2, x._3))
    }
    Ok(views.html.acp.resources("ACP Resources")(resources))
  }
}