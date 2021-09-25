package jeet.assign.cc1.simulations

import jeet.assign.cc1.utils._
import org.cloudbus.cloudsim.allocationpolicies.VmAllocationPolicyRoundRobin
import org.cloudbus.cloudsim.brokers.DatacenterBrokerSimple
import org.cloudbus.cloudsim.cloudlets.Cloudlet
import org.cloudbus.cloudsim.core.CloudSim
import org.cloudbus.cloudsim.vms.Vm
import org.cloudsimplus.builders.tables.CloudletsTableBuilder
import org.slf4j.{Logger, LoggerFactory}

import java.util
import java.util.Comparator
import scala.runtime.Nothing$

class RRVmAlloc(val simNo: Int)
{
  val logger: Logger = LoggerFactory.getLogger(this.getClass.getSimpleName)
  logger.info("Starting RoundRObin Simulation.. \n")

  val simModel = "RoundRobin"
  val index = 1

  val simulation = new CloudSim
  val specsOfHost = new HostConfig(simModel, simNo, index)
  val specsOfDC = new DataCenterConfig(simModel, simNo, index)
  val specsOfVM = new VMConfig(simModel, simNo, index)
  val specsOfCloudLet = new CloudletConfig(simModel, simNo, index)

  val DCUtil = new DataCenterUtil(specsOfHost, specsOfDC, specsOfVM, specsOfCloudLet)

  DCUtil.datacenter(simulation, new VmAllocationPolicyRoundRobin, false)

  val broker = new DatacenterBrokerSimple(simulation)

  logger.info("Creating lists of VM & Cloudlets..\n")
  val vmlist: util.List[Vm] = DCUtil.vmlist
  broker.submitVmList(vmlist)

  val clletlist: util.List[Cloudlet] = DCUtil.clletList
  broker.submitCloudletList(clletlist)

  simulation.start

  val clletover: util.List[Cloudlet] = broker.getCloudletFinishedList
  //clletover.sort(Comparator.comparingLong((cloudlet: Cloudlet)) => cloudlet.getVm.getId): Nothing)
  new CloudletsTableBuilder(clletover).build()
  System.out.println();

  logger.info("End of RoundRobin Simulation \n")
}
