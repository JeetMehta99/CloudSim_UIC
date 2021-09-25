package jeet.assign.cc1.simulations


import jeet.assign.cc1.utils.{CloudletConfig, DataCenterConfig, DataCenterUtil, HostConfig, VMConfig}
import org.cloudbus.cloudsim.brokers.DatacenterBrokerSimple
import org.cloudbus.cloudsim.cloudlets.Cloudlet
import org.cloudbus.cloudsim.core.CloudSim
import org.cloudbus.cloudsim.schedulers.cloudlet.CloudletScheduler
import org.cloudbus.cloudsim.schedulers.vm.VmScheduler
import org.cloudbus.cloudsim.vms.Vm
import org.cloudsimplus.builders.tables.CloudletsTableBuilder
import org.slf4j.{Logger, LoggerFactory}

import java.util

class CloudletexecSimulation(val simNo: Int, val simModel: String, val vmScheduler: VmScheduler, val cloudletScheduler: CloudletScheduler)
{
  val logger = LoggerFactory.getLogger(this.getClass.getSimpleName)
  logger.info("Creating VM Simulation model: \n",  simModel)

  val index = 1

  val simulation = new CloudSim
  val specsOfHost = new HostConfig(simModel, simNo, index)
  val specsOfCloudLet = new CloudletConfig(simModel, simNo, index)
  val specsOfVM = new VMConfig(simModel, simNo, index)
  val specsOfDC = new DataCenterConfig(simModel, simNo, index)

  val DCUtil = new DataCenterUtil(specsOfHost, specsOfDC, specsOfVM, specsOfCloudLet)
  DCUtil.datacenter(simulation, vmScheduler)

  val broker = new DatacenterBrokerSimple(simulation)

  logger.info("Creating VM & Cloudlets..\n")
  val vm: Vm = DCUtil.vm(cloudletScheduler)
  val clletlist: util.List[Cloudlet] = DCUtil.clletList
  broker.submitVm(vm)
  broker.submitCloudletList(clletlist)

  simulation.start

  val clletover: util.List[Cloudlet] = broker.getCloudletFinishedList
  new CloudletsTableBuilder(clletover).build()

  logger.info("End of Vm simulation: ", simModel)
}
