package jeet.assign.cc1.utils

import com.typesafe.config.{Config, ConfigFactory}
import jeet.assign.cc1.utils.DataCenterUtil
import jeet.assign.cc1.utils.{CloudletConfig, DataCenterConfig, DataCenterUtil, HostConfig, VMConfig}
import org.cloudbus.cloudsim.core.CloudSim
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

class DCtest extends AnyFlatSpec with Matchers {
  behavior of "DataCenter is created or not"


  val simulation = new CloudSim
  val index = 1
  val simModel = "SaasSim"
  val specsOfHost = new HostConfig(simModel, 2, index)
  val specsOfCloudLet = new CloudletConfig(simModel, 2, index)
  val specsOfVM = new VMConfig(simModel, 2, index)
  val specsOfDC = new DataCenterConfig(simModel, 2, index)

  val dcUtils = new DataCenterUtil(specsOfHost, specsOfDC, specsOfVM, specsOfCloudLet)
  val cloudsim = new CloudSim()
  val config: Config = ConfigFactory.load("DCsimul.conf")
  val hostlist = dcUtils.host()

  val datac = dcUtils.datacenter(cloudsim)
  datac shouldBe a [DataCenterUtil]

}