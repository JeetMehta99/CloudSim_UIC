package jeet.assign.cc1

import jeet.assign.cc1.simulations.*
import org.cloudbus.cloudsim.schedulers.cloudlet.{CloudletSchedulerSpaceShared, CloudletSchedulerTimeShared}
import org.cloudbus.cloudsim.schedulers.vm.{VmSchedulerSpaceShared, VmSchedulerTimeShared}
import org.slf4j.Logger
import org.slf4j.LoggerFactory


object execSimulation
{
  private val logger = LoggerFactory.getLogger(this.getClass)
  def main(args: Array[String]): Unit =
  {
    logger.info("***** Execute Simulation *****\n")
    logger.info("----- VM Scheduler Simulations -----\n")
    logger.info("$$$$$ Time Shared VM Scheduler Simulation $$$$$\n")

    new CloudletexecSimulation( 1,"TimeShared" ,new VmSchedulerTimeShared, new CloudletSchedulerTimeShared)
    logger.info("@@@@@ Space Shared VM Scheduler Simulation @@@@@\n")
    new CloudletexecSimulation(1,"SpaceShared",new VmSchedulerSpaceShared, new CloudletSchedulerSpaceShared)
    logger.info("##### Round Robin VM Allocation Simulation #####\n")
    new RRVmAlloc(1)

    logger.info("----- Saas Simulations -----\n")
    logger.info("_____ Saas Simulation: 1 _____\n")
    new SaasSimulation(1)

    logger.info("_____ Saas Simulation: 2 _____\n")
    new SaasSimulation(2)

    logger.info("End of Saas Simulations\n")

    logger.info("----- Simulating all DataCenters for Tasks:-----\n")
    new SimDC

    logger.info("***** End of Simulations *****\n")
  }
}