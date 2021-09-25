package jeet.assign.cc1.utils

import jeet.assign.cc1.simulations.CloudletexecSimulation
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

import com.typesafe.config.{Config, ConfigFactory}
class BasicCloudSimPlusExampleTestSuite extends AnyFlatSpec with Matchers
{
  behavior of "configuration parameters module"

  it should "obtain the get cost per second" in {
    val config: Config = ConfigFactory.load("DCsimul.conf")
    config.getDouble("simulation1.datacenter1.getcpsecond") shouldBe 1.0E0
  }

  it should "obtain the MIPS capacity" in {
    val config: Config = ConfigFactory.load("application.conf")
    config.getLong("simulation1.datacenter1.vm.mips") shouldBe 1000
  }
}
