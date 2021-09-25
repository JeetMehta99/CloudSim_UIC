package jeet.assign.cc1.utils



import com.typesafe.config.Config
import com.typesafe.config.ConfigFactory


class DataCenterConfig(val sim_model: String, val SimNo: Int, val index: Int)
{
  
  val conf = ConfigFactory.load(sim_model)
  val util_config: String = "simulation" + SimNo + "." + "dataCenter" + index + "."
  val totalhosts = conf.getInt(util_config + "totalhosts")
  val totalCLs = conf.getInt(util_config + "totalCLs")
  val totalVMs = conf.getInt(util_config + "totalVMs")
  val arch = conf.getString(util_config + "arch")
  val os = conf.getString(util_config + "os")
  val vmm = conf.getString(util_config + "vmm")
  val getcpsecond = conf.getDouble(util_config + "getcpsecond")
  val getcpmemory = conf.getDouble(util_config + "getcpmemory")
  val getcpstorage = conf.getDouble(util_config + "getcpstorage")
  val getcpbw = conf.getDouble(util_config + "getcpbw")

}