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
            warehouses.add(a);

        }

        for (int i = 0; i < configDetail.getFreight()[0]; i++) {

            freight a = new freight(i, configDetail.getFreight()[1]);
            freights.add(a);

        }

        for (int i = 0; i < configDetail.getSupplier()[0]; i++) {

            SupplierThread a = new SupplierThread("SupplierThread_" + i, warehouses,
                    configDetail.getSupplier()[1], configDetail.getSupplier()[2], configDetail.getDay());

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
        for (int j = 1; j <= configDetail.getDay(); j++) {

            for (int i = 0; i < freights.size(); i++) {
                freights.get(i).reset();
            }
            System.out.println("main >> DAY " + j );
            
            monitorS.wakeUpThreads();
            monitorM.waitForThreads();

        }

    }

    public static void main(String[] args) {
        main app = new main();
        app.runSimulaion();

    }

}
