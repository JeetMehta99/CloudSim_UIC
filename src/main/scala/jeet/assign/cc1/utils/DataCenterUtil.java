package jeet.assign.cc1.utils;

import org.cloudbus.cloudsim.allocationpolicies.VmAllocationPolicy;
import org.cloudbus.cloudsim.allocationpolicies.VmAllocationPolicySimple;
import org.cloudbus.cloudsim.cloudlets.Cloudlet;
import org.cloudbus.cloudsim.cloudlets.CloudletSimple;
import org.cloudbus.cloudsim.core.CloudSim;
import org.cloudbus.cloudsim.datacenters.Datacenter;
import org.cloudbus.cloudsim.datacenters.DatacenterSimple;
import org.cloudbus.cloudsim.hosts.Host;
import org.cloudbus.cloudsim.hosts.HostSimple;
import org.cloudbus.cloudsim.resources.Pe;
import org.cloudbus.cloudsim.resources.PeSimple;
import org.cloudbus.cloudsim.schedulers.cloudlet.CloudletScheduler;
import org.cloudbus.cloudsim.schedulers.cloudlet.CloudletSchedulerTimeShared;
import org.cloudbus.cloudsim.schedulers.vm.VmScheduler;
import org.cloudbus.cloudsim.schedulers.vm.VmSchedulerSpaceShared;
import org.cloudbus.cloudsim.utilizationmodels.UtilizationModel;
import org.cloudbus.cloudsim.utilizationmodels.UtilizationModelFull;
import org.cloudbus.cloudsim.vms.Vm;
import org.cloudbus.cloudsim.vms.VmSimple;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;


public class DataCenterUtil
{
    private static final Logger logger = LoggerFactory.getLogger(DataCenterUtil.class.getSimpleName());
    private final HostConfig hostconfig;
    private final CloudletConfig cloudletconfig;
    private final VMConfig VMconfig;
    private final DataCenterConfig DCconfig;

    public DataCenterUtil(HostConfig hostconfig, DataCenterConfig DCconfig,VMConfig VMconfig, CloudletConfig cloudletconfig)
    {
        this.hostconfig = hostconfig;
        this.DCconfig = DCconfig;
        this.VMconfig = VMconfig;
        this.cloudletconfig = cloudletconfig;


        logger.info("Loading Configurations: \n");
        logger.info("Total Hosts:{} \n", DCconfig.totalhosts());
        logger.info("Total Cloudlets:{} \n", DCconfig.totalCLs());
        logger.info("Total Vms:{} \n", DCconfig.totalVMs());
    }
    public List<Pe> PeList()
    {
        List<Pe> list = new ArrayList<>();
        IntStream.range(0, hostconfig.numberOfPE())
                .forEach(i ->
                {
                    list.add(new PeSimple(hostconfig.mips()));
                });

        System.out.println();
        return list;
    }
    public Host host(boolean hostact, VmScheduler vmschedule) throws InstantiationException, IllegalAccessException
    {
        List<Pe> PE_list = PeList();
        VmScheduler schedule = vmschedule.getClass().newInstance();

        return new HostSimple(hostconfig.ram(), hostconfig.bw(),hostconfig.storage(),PE_list,hostact)
                .setVmScheduler(schedule);
    }

    public Host host() throws InstantiationException, IllegalAccessException
    {
        return host(true, new VmSchedulerSpaceShared());
    }

    public List<Host> HostList(boolean hostact, VmScheduler vmschedule)
    {
        List<Host> list = new ArrayList<>();
        logger.info("Host: {} \n", DCconfig.totalhosts());
        IntStream.range(0, DCconfig.totalhosts())
                .forEach(i ->
                {
                    logger.info("PEs added to Host {} ", i);

        try
        {
            list.add(host(hostact, vmschedule));
        } catch (IllegalAccessException | InstantiationException e)
        {
            e.printStackTrace();
        }

    });


        return list;
    }

    public List<Host> HostList()
            
    {
        return HostList(true, new VmSchedulerSpaceShared());
    }

    public Datacenter datacenter(CloudSim simulation, VmAllocationPolicy vmallocpolicy, VmScheduler vmschedule, boolean hostact)
    {
        List<Host> hostlist= HostList(hostact, vmschedule);
        Datacenter dc = new DatacenterSimple(simulation, hostlist, vmallocpolicy );
        dc.getCharacteristics()
                .setArchitecture(DCconfig.arch())
                .setOs(DCconfig.os())
                .setVmm(DCconfig.vmm())
                .setCostPerSecond(DCconfig.getcpsecond())
                .setCostPerMem(DCconfig.getcpmemory())
                .setCostPerStorage(DCconfig.getcpstorage())
                .setCostPerBw(DCconfig.getcpbw());
        return dc;
    }
    public Datacenter datacenter(CloudSim simulation, VmAllocationPolicy vmallocpolicy, boolean hostact)
    {
        return datacenter(simulation, vmallocpolicy, new VmSchedulerSpaceShared(), hostact);
    }

    public Datacenter datacenter(CloudSim simulation, VmAllocationPolicy vmallocpolicy)
    {
        return datacenter(simulation, vmallocpolicy, new VmSchedulerSpaceShared(), true);
    }

    public Datacenter datacenter(CloudSim simulation, VmScheduler vmschedule)
    {
        return datacenter(simulation, new VmAllocationPolicySimple(), vmschedule, true);
    }

    public Datacenter datacenter(CloudSim simulation, boolean hostact)
    {
        return datacenter(simulation, new VmAllocationPolicySimple(), new VmSchedulerSpaceShared(),true);
    }

    public Datacenter datacenter(CloudSim simulation)
    {
        return datacenter(simulation,true);
    }

    public Vm vm(CloudletScheduler cloudletschedule)
    {
        return new VmSimple(VMconfig.mips(), VMconfig.numberOfPE(), cloudletschedule)
                .setRam(VMconfig.ram())
                .setBw(VMconfig.bw())
                .setSize(VMconfig.size());
    }
    public Vm vm()
    {
        return vm(new CloudletSchedulerTimeShared());
    }

    public List<Vm> vmlist(CloudletScheduler cloudletschedule)
    {
        List<Vm> list = new ArrayList<>();
        IntStream.range(0, DCconfig.totalVMs())
                .forEach(i -> list.add(vm(cloudletschedule))); //for self: could replace with function lambda
        System.out.println();
        return list;
    }
    public List<Vm> vmlist()
    {
        return vmlist(new CloudletSchedulerTimeShared());
    }

    public Cloudlet cllet(UtilizationModel utilModel)
    {
        // return new cloudlet instance
        return new CloudletSimple(cloudletconfig.length(), cloudletconfig.numberOfPE(), utilModel)
                .setSizes(cloudletconfig.size());
    }

    public Cloudlet cllet()
    {
        return cllet(new UtilizationModelFull());
    }

    public List<Cloudlet> clletList(UtilizationModel utilModel)
    {
        List<Cloudlet> list = new ArrayList<>();
        logger.info("Cloudlets {}\n", DCconfig.totalCLs());
        IntStream.range(0, DCconfig.totalCLs())
                .forEach(i -> list.add(cllet(utilModel)));
        return list;
    }

    public List<Cloudlet> clletList()
    {
        return clletList(new UtilizationModelFull());
    }

    public static double execcost(List<Cloudlet> cloudlet)
    {
        double cost = 0.0;
        for (Cloudlet cl : cloudlet)
        {
            cost += cl.getTotalCost();
            logger.info("Cloudlet {} cost on DataCenter {} is {} \n"
                    , cl.getId(), cl.getLastTriedDatacenter().getId(), (double) cl.getTotalCost());
        }
        return cost;
    }
}
