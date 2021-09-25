package jeet.assign.cc1.simulations


import jeet.assign.cc1.utils._
import org.cloudbus.cloudsim.brokers.DatacenterBrokerSimple //A simple implementation of DatacenterBroker that try to host customer's VMs at the first Datacenter found.
import org.cloudbus.cloudsim.cloudlets.Cloudlet //rovides Cloudlet implementations, that represent an application that will run inside a Vm.
import org.cloudbus.cloudsim.core.CloudSim //Provides core classes used just internally by CloudSim Plus, except the CloudSim class that is the start point and main class used to run simulations.
import org.cloudbus.cloudsim.vms.Vm //Provides implementations of Virtual Machines (Vm) which are a software package that emulate the architecture of a physical machin
import org.cloudsimplus.builders.tables.CloudletsTableBuilder //Provides Table classes that are used to format simulation results in different and structured ways such as ASCII, CSV or HTML tables.
import org.slf4j.{Logger, LoggerFactory}

import java.util
import collection.JavaConverters.seqAsJavaListConverter

class SaasSimulation(val simNo: Int)
{
  val logger: Logger = LoggerFactory.getLogger(this.getClass.getSimpleName)

  val simulation = new CloudSim
  val index = 1
  val simModel = "SaasSimul"
  logger.info("Saas Simulation starts..\n")

  val specsOfHost = new HostConfig(simModel, simNo, index)
  val specsOfDC = new DataCenterConfig(simModel, simNo, index)
  val specsOfVM = new VMConfig(simModel, simNo, index)
  val specsOfCloudLet = new CloudletConfig(simModel, simNo, index)


  val DCUtil = new DataCenterUtil(specsOfHost, specsOfDC, specsOfVM, specsOfCloudLet)
  DCUtil.datacenter(simulation)

  val broker0 = new DatacenterBrokerSimple(simulation)
  logger.info("Creating lists of VM & Cloudlets.. \n")

  val vmlist: util.List[Vm] = DCUtil.vmlist
  broker0.submitVmList(vmlist)

  val clletList: util.List[Cloudlet] = DCUtil.clletList
  broker0.submitCloudletList(clletList)

  simulation.start

  val clletover: util.List[Cloudlet] = broker0.getCloudletFinishedList
  new CloudletsTableBuilder(clletover).build()
  logger.info("Cost: " + DataCenterUtil.execcost(clletover))

  logger.info("End of Saas Simulation. \n")
}
