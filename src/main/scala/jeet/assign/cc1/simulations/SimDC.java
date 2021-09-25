package jeet.assign.cc1.simulations;

import jeet.assign.cc1.utils.*;
import org.cloudbus.cloudsim.brokers.DatacenterBroker;
import org.cloudbus.cloudsim.brokers.DatacenterBrokerSimple;
import org.cloudbus.cloudsim.cloudlets.Cloudlet;
import org.cloudbus.cloudsim.core.CloudSim;

import org.cloudbus.cloudsim.datacenters.Datacenter;
import org.cloudbus.cloudsim.network.topologies.BriteNetworkTopology; //Implements a network layer by reading the topology from a file in the BRITE format, the Boston university Representative Internet Topology gEnerator, and generates a topological network from it.
import org.cloudbus.cloudsim.schedulers.vm.VmSchedulerSpaceShared;
import org.cloudbus.cloudsim.vms.Vm;
import org.cloudsimplus.builders.tables.CloudletsTableBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.ArrayList;

import java.util.List;


public class SimDC
{
    CloudSim simulation;
    Datacenter D_C1, D_C2, D_C3;
    DatacenterBroker broker;

    public SimDC()
    {
        Logger logger = LoggerFactory.getLogger(this.getClass().getSimpleName());
        simulation = new CloudSim();


        String s = "application";
        String dc1 = "DCsimul";
        IaasSimulation specs = new IaasSimulation(dc1, 1);
        IaasSimulation ISspecs = new IaasSimulation(s, 1);
        PaasSimulation PSspecs = new PaasSimulation(dc1, 2);

        DataCenterUtil iaas = new DataCenterUtil(ISspecs.getHostconfig(), ISspecs.getDCconfig(), ISspecs.getVMconfig(), ISspecs.getCloudletconfig());
        DataCenterUtil paas = new DataCenterUtil(specs.getHostconfig(), specs.getDCconfig(), specs.getVMconfig(), PSspecs.getCloudletconfig());
        DataCenterUtil saas = new DataCenterUtil(specs.getHostconfig(), specs.getDCconfig(), specs.getVMconfig(), specs.getCloudletconfig());
        D_C1 = iaas.datacenter(simulation, new VmSchedulerSpaceShared());
        D_C2 = paas.datacenter(simulation);
        D_C3 = saas.datacenter(simulation);

        broker = new DatacenterBrokerSimple(simulation);

        connectDataCenters();

        List<Vm> vm_List = new ArrayList<>();

        //ForSelf:Optional.ofNullable(listToBeAdded).ifPresent(listToBeAddedTo::addAll)
        //ForSelf:listToBeAdded - The list whose elements are to be added. listToBeAddedTo - The list to which you are adding elements using addAll.
        vm_List.addAll(iaas.vmlist());
        vm_List.addAll(paas.vmlist());
        vm_List.addAll(saas.vmlist());
        broker.submitVmList(vm_List);

        List<Cloudlet> clletlist = new ArrayList<>();
        clletlist.addAll(iaas.clletList());
        clletlist.addAll(paas.clletList());
        clletlist.addAll(saas.clletList());
        broker.submitCloudletList(clletlist);
        simulation.start();



        final List<Cloudlet> clletover = broker.getCloudletFinishedList();

        new CloudletsTableBuilder(clletover).build();
        System.out.println("Total Cost of cloudlets= " + DataCenterUtil.execcost(clletover));

        logger.info("\nEnd Simulation");
    }


    private void connectDataCenters()
    {
        BriteNetworkTopology networkTopology = BriteNetworkTopology.getInstance("topology.brite");
        simulation.setNetworkTopology(networkTopology);

        networkTopology.mapNode(D_C1,0);
        networkTopology.mapNode(D_C2,2);
        networkTopology.mapNode(D_C3,3);
        networkTopology.mapNode(broker,4);
    }
}

class IaasSimulation
{
    private final HostConfig hostconfig;
    private final CloudletConfig cloudletconfig;
    private final VMConfig VMconfig;
    private final DataCenterConfig DCconfig;

    public IaasSimulation(String s1, int simNo)
    {
        this.hostconfig = new HostConfig(s1, simNo, 1);
        this.cloudletconfig = new CloudletConfig(s1, simNo, 1);
        this.VMconfig = new VMConfig(s1, simNo, 1);
        this.DCconfig = new DataCenterConfig(s1, simNo, 1);
    }
    public HostConfig getHostconfig()
    {
        return hostconfig;
    }

    public CloudletConfig getCloudletconfig()
    {
        return cloudletconfig;
    }

    public VMConfig getVMconfig()
    {
        return VMconfig;
    }

    public DataCenterConfig getDCconfig()
    {
        return DCconfig;
    }

}
class PaasSimulation
{
    private final CloudletConfig cloudletconfig;
    public PaasSimulation(String s1, int simNo)
    {
        this.cloudletconfig = new CloudletConfig(s1, simNo, 1);

    }
    public CloudletConfig getCloudletconfig()
    {
        return cloudletconfig;
    }
}

