package GPRO2JAVA;

import java.io.*;
import java.util.*;

public class main {

    protected config configDetail;
    //protected ArrayList<Warehouse> warehouses = new ArrayList<>();
    //protected ArrayList<Freight> freights = new ArrayList<>();
    //protected ArrayList<SupplierThread> suppliers = new ArrayList<>();
   // protected ArrayList<FactoryThread> factories = new ArrayList<>();


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
       
       
       //create thread
       //run thread
       //print final
       
       
       
       
    }

    public static void main() {
        main app = new main();
        app.runSimulaion();

    }

}
