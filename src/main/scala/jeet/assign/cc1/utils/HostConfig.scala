package jeet.assign.cc1.utils

import com.typesafe.config.ConfigFactory
import com.typesafe.config.Config


class HostConfig(val sim_model: String, val SimNo: Int, val index: Int)
{
  val conf = ConfigFactory.load(sim_model)
  val util_config: String = "simulation" + SimNo + "." + "host" + index + "."
  val mips = conf.getLong(util_config + "mips")
  val ram = conf.getInt(util_config + "ram")
  val storage = conf.getInt(util_config + "storage")
  val bw = conf.getInt(util_config + "bw")
  val numberOfPE = conf.getInt(util_config + "numberOfPE")
  
}
