package GPRO2JAVA;

import java.io.*;
import java.util.*;
import java.util.concurrent.*;

public class main {

    protected config configDetail;
    protected ArrayList<warehouse> warehouses = new ArrayList<>();
    protected ArrayList<freight> freights = new ArrayList<>();
    protected ArrayList<SupplierThread> suppliers = new ArrayList<>();
    protected ArrayList<FactoryThread> factories = new ArrayList<>();
    private MyMonitor monitorS, monitorM, monitorF;

    public void runSimulaion() {
        monitorS = new MyMonitor();
        monitorM = new MyMonitor();
        monitorF = new MyMonitor();
        ArrayList<Integer> data = new ArrayList<>();
        ArrayList<String> war = new ArrayList<>();
        ArrayList<String> fre = new ArrayList<>();
        ArrayList<String> sup = new ArrayList<>();
        ArrayList<String> fac = new ArrayList<>();
        String path = "src/main/java/GPRO2JAVA/";
        Scanner sc = new Scanner(System.in);

        boolean opensuccess = false;
        while (!opensuccess) {
            System.out.println("New file name =");
            String file = sc.nextLine().trim();
            try (Scanner br = new Scanner(new File(path + file));) {
                opensuccess = true;
                while (br.hasNextLine()) {
                    String line = br.nextLine();
                    String[] parts = line.split(",");

                    for (String part : parts) {
                        part = part.trim();
                        try {
                            int num = Integer.parseInt(part);
                            data.add(num);
                        } catch (NumberFormatException e) {

                        }
                    }
                }
                br.close();
            } catch (Exception e) {
            }
        }
        configDetail = new config(data);
        

        ////////// create thread ////////////////
        for (int i = 0; i < configDetail.getWarehouse(); i++) {

            warehouse a = new warehouse("Warehouse_" + i);
            war.add("Warehouse_" + i);
            warehouses.add(a);

        }

        for (int i = 0; i < configDetail.getFreight()[0]; i++) {

            freight a = new freight(i, configDetail.getFreight()[1]);
            fre.add("Freight_" + i);
            freights.add(a);

        }

        for (int i = 0; i < configDetail.getSupplier()[0]; i++) {

            SupplierThread a = new SupplierThread("SupplierThread_" + i, warehouses,
                    configDetail.getSupplier()[1], configDetail.getSupplier()[2], configDetail.getDay());
            sup.add("SupplierThread_" + i);
            suppliers.add(a);

        }
        CyclicBarrier barrierS = new CyclicBarrier(suppliers.size());
        for (int i = 0; i < suppliers.size(); i++) {
            suppliers.get(i).setBarrier(barrierS);
            suppliers.get(i).setMonitorS(monitorS);
            suppliers.get(i).setMonitorF(monitorF);
            suppliers.get(i).start();
        }
        for (int i = 0; i < configDetail.getFactory()[0]; i++) {

            FactoryThread a = new FactoryThread("FactoryThread_" + i, configDetail.getDay(), configDetail.getFactory()[1]);
            fac.add("FactoryThread_" + i);
            factories.add(a);
        }
        CyclicBarrier barrierF = new CyclicBarrier(factories.size());
        for (int i = 0; i < factories.size(); i++) {
            factories.get(i).setBarrier(barrierF);
            factories.get(i).setMonitorF(monitorF);
            factories.get(i).setMonitorM(monitorM);
            factories.get(i).setWarehouses(warehouses);
            factories.get(i).setFreights(freights);
            factories.get(i).start();
        }
        ////////print  config data //////////////
        System.out.println("================= Parameters =================");
        System.out.printf("Days of simulation : %2d\n",configDetail.getDay());
        System.out.print("Warehouses         : ");
        System.out.println(war);
        System.out.printf("Freights           : ");
        System.out.println(fre);
        System.out.printf("Freight capacity   : max = %3d\n",configDetail.getFreight()[1]);
        System.out.printf("SupplierThreads    : ");
        System.out.println(sup);
        System.out.printf("Daily supply       : min = %3d, max = %3d\n",configDetail.getSupplier()[1],configDetail.getSupplier()[2]);
        System.out.printf("FactoryThreads     : ");
        System.out.println(fac);
        System.out.printf("Daily production   : max = %3d\n",configDetail.getFactory()[1]);
        
        
        for (int j = 1; j <= configDetail.getDay(); j++) {

            for (int i = 0; i < freights.size(); i++) {
                freights.get(i).reset();
            }
            System.out.println("main >> DAY " + j);

            monitorS.wakeUpThreads();
            monitorM.waitForThreads();

        }

    }

    public static void main(String[] args) {
        main app = new main();
        app.runSimulaion();

    }

}
