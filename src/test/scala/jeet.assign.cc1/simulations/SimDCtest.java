package jeet.assign.cc1.simulations;


import jeet.assign.cc1.utils.*;
import org.cloudbus.cloudsim.brokers.DatacenterBroker;
import org.cloudbus.cloudsim.brokers.DatacenterBrokerSimple;
import org.cloudbus.cloudsim.cloudlets.Cloudlet;
import org.cloudbus.cloudsim.core.CloudSim;
import org.cloudbus.cloudsim.datacenters.Datacenter;
import org.cloudbus.cloudsim.vms.Vm;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import java.util.List;



class SimDCtest
{
    static DataCenterUtil DCUtil;
    static HostConfig hostConfig;
    static DataCenterConfig dataCenterConfig;
    static VMConfig vmConfig;
    static CloudletConfig cloudletConfig;

    @BeforeAll
    static void init()
    {
        String simModel = "SaasSimul";
        int simNo = 1;
        int index = 1;
        hostConfig = new HostConfig(simModel, simNo, index);
        dataCenterConfig = new DataCenterConfig(simModel,simNo,index);
        vmConfig = new VMConfig(simModel,simNo,index);
        cloudletConfig = new CloudletConfig(simModel,simNo,index);

        DCUtil = new DataCenterUtil(hostConfig, dataCenterConfig, vmConfig, cloudletConfig);

    }
    @Test
    void testSimDC()
    {
        CloudSim simulation = new CloudSim();
        Datacenter dc = DCUtil.datacenter(simulation);
        Assertions.assertNotNull(dc);

        DatacenterBroker broker = new DatacenterBrokerSimple(simulation);

        List<Vm> vmList = DCUtil.vmlist();
        Assertions.assertNotNull(vmList);

        List<Cloudlet> clletlist = DCUtil.clletList();
        Assertions.assertNotNull(clletlist);

        broker.submitVmList(vmList);
        broker.submitCloudletList(clletlist);

        simulation.start();

        Assertions.assertFalse(simulation.isRunning());
        Assertions.assertEquals(broker.getVmCreatedList().size(), vmList.size()); //forself: cannot resolve method in case of array equals
        Assertions.assertEquals(broker.getCloudletFinishedList().size(), clletlist.size());

        //ForSelf: Call to 'assertEquals()' from 'Assert' should be replaced with call to method from 'org.junit.jupiter.api.Assertions

    }



}


