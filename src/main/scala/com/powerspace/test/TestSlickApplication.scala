package com.powerspace.test

import slick.jdbc.PostgresProfile.api._
import Tables._
import com.typesafe.scalalogging.LazyLogging

import scala.concurrent.duration._
import scala.concurrent.Await
import scala.concurrent.ExecutionContext.Implicits.global

object TestSlickApplication extends LazyLogging {

  case class EncapsulateWebsite(website: Website, adtemplates: Seq[AdTemplateImage])

  def main(args: Array[String]): Unit = {
    logger.debug("Start it")

    val db = Database.forConfig("db.config")

    val encapsulatedWebsiteQuery = for {
      ((website, position), adtemplate) <- websites
        .join(positions).on { (web, pos) => web.id === pos.websiteId }
        .joinLeft(adtemplateImages).on { case ((_, pos), ad) => pos.id === ad.positionId }
    } yield (website, position, adtemplate)


    val result = db.run(encapsulatedWebsiteQuery.result)
    result.map(r => EncapsulateWebsite(r.head._1, r.map(_._3).filter(_.isDefined).map(_.get)))
    println(Await.result(result, 1 seconds).size)
  }
}
