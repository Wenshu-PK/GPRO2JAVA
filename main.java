package GPRO2JAVA;

import java.io.*;
import java.util.*;

public class main {

    protected config configDetail;
    protected ArrayList<warehouse> warehouses = new ArrayList<>();
    protected ArrayList<freight> freights = new ArrayList<>();
    protected ArrayList<SupplierThread> suppliers = new ArrayList<>();
    protected ArrayList<FactoryThread> factories = new ArrayList<>();
    private MyMonitor monitorS, monitorM;

    public void runSimulaion() {
        ArrayList<Integer> data = new ArrayList<>();
        String filePath = "src/main/java/Project2_6713249/Config.java";

        Scanner scan_file = new Scanner(filePath);
        try {
            while (scan_file.hasNext()) {
                if (scan_file.hasNextInt()) {
                    data.add(scan_file.nextInt());
                } else {
                    scan_file.next();
                }
            }
        } catch (Exception e) {
            System.out.println(e);
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
            a.setMonitorS(monitorS);
            a.setMonitorM(monitorM);
            a.start();
            suppliers.add(a);

        }
        for (int i = 0; i < configDetail.getFactory()[0]; i++) {

            FactoryThread a = new FactoryThread("FactoryThread_" + i, configDetail.getDay(), configDetail.getFactory()[1]);
            a.setMonitorS(monitorS);
            a.setMonitorM(monitorM);
            a.start();
            factories.add(a);
        }

        for (int j = 0; j < configDetail.getDay(); j++) {

            System.out.println("main >> DAY " + j + 1);
            monitorS.wakeUpThreads();
            monitorM.waitForThreads();

        }

    }

    public static void main() {
        main app = new main();
        app.runSimulaion();

    }

}
