package jeet.assign.cc1.utils

import com.typesafe.config.{Config, ConfigFactory}
import jeet.assign.cc1.utils.CreateLogger
import scala.util.{Failure, Success, Try}

class ObtainConfigReference
object ObtainConfigReference:
  private val config = ConfigFactory.load()
  private val logger = CreateLogger(classOf[ObtainConfigReference])
  private def ValidateConfig(confEntry: String):Boolean = Try(config.getConfig(confEntry)) match {
    case Failure(exception) => logger.error(s"Failed to retrieve config entry $confEntry for reason $exception"); false
    case Success(_) => true
  }

  def apply(confEntry:String): Option[Config] = if ValidateConfig(confEntry) then Some(config) else None

