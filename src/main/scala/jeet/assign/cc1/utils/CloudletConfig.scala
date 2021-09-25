package jeet.assign.cc1.utils

import com.typesafe.config.Config
import com.typesafe.config.ConfigFactory

class CloudletConfig(val sim_model: String, val SimNo: Int, val index: Int)
{
  val conf = ConfigFactory.load(sim_model)
  val util_config = "simulation" + SimNo + "." + "cloudlet" + index + "."
  val size = conf.getLong(util_config + "size")
  val numberOfPE = conf.getInt(util_config + "numberOfPE")
  val length = conf.getLong(util_config + "length")

}