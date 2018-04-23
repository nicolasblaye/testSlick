package com.powerspace.test


object Tables {

  case class AdTemplateImage(id: Int, width: Int, height: Int, positionId: Int, name: String, isActive: Boolean, originalImage: Option[String], backgroundColor: Option[String])
  case class Position(id: Int, name: String, shortCode: String, isActive: Boolean, websiteId: Int)
  case class Website(id: Int, name: String)



  import slick.jdbc.PostgresProfile.api._
  import slick.lifted.ProvenShape

  class AdTemplateImageTable(tag: Tag) extends Table[AdTemplateImage](tag, "st_adtemplate_image") {
    def id = column[Int]("id", O.PrimaryKey)
    def width = column[Int]("width")
    def height = column[Int]("height")
    def positionId = column[Int]("position_id")
    def name = column[String]("name")
    def isActive = column[Boolean]("is_active")
    def originalImage = column[Option[String]]("original_image")
    def backgroundColor = column[Option[String]]("background_color")

    def position = foreignKey("position_fk", positionId, positions)(_.id)

    override def * : ProvenShape[AdTemplateImage] = (id, width, height, positionId, name, isActive, originalImage, backgroundColor) <> (AdTemplateImage.tupled, AdTemplateImage.unapply)
  }

  class PositionTable(tag: Tag) extends Table[Position](tag, "st_position") {
    def id = column[Int]("id", O.PrimaryKey)
    def name = column[String]("name")
    def shortCode = column[String]("short_code")
    def isActive = column[Boolean]("is_active")
    def websiteId = column[Int]("website_id")
    override def * : ProvenShape[Position] = (id, name, shortCode, isActive, websiteId) <> (Position.tupled, Position.unapply)
  }

  class WebsiteTable(tag: Tag) extends Table[Website](tag, "st_web_site") {
    def id = column[Int]("id", O.PrimaryKey)
    def name = column[String]("name")

    override def * = (id, name) <> (Website.tupled, Website.unapply)
  }

  val adtemplateImages: TableQuery[Tables.AdTemplateImageTable] = TableQuery[AdTemplateImageTable]
  val positions = TableQuery[PositionTable]
  val websites = TableQuery[WebsiteTable]
}
