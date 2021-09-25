package jeet.assign.cc1.utils

import com.typesafe.config.Config
import com.typesafe.config.ConfigFactory


class VMConfig(val sim_model: String, val SimNo: Int, val index: Int) {
  val conf = ConfigFactory.load(sim_model)
  val util_config: String = "simulation" + SimNo + "." + "vm" + index + "."
  val mips = conf.getInt(util_config + "mips")
  val ram = conf.getInt(util_config + "ram")
  val size = conf.getLong(util_config + "size")
  val bw = conf.getInt(util_config + "bw")
  val numberOfPE = conf.getInt(util_config + "numberOfPE")
  val vmm = conf.getString(util_config + "vmm")

}
