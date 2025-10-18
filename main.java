package GPRO2JAVA;

import java.io.*;
import java.util.*;

/**
 *
 * @author sawan
 */
public class main {

    protected config configDetail;

    public void runSimulaion() {
        ArrayList<Integer> data = new ArrayList<>();
        String filePath = "src/main/java/Project2_6713249/Config.java";
        Scanner scan_file = new Scanner(filePath);
        try {
            while (scan_file.hasNext()) {
                if (scan_file.hasNextInt()) {
                    data.add(scan_file.nextInt());
                } else {
                    scan_file.next(); // skip non-integer tokens
                }
            }
        } catch (Exception e) {
            System.out.println(e);
        }
       configDetail = new config(data);
       
       
       
       
       
       
    }

    public static void main() {
        main app = new main();
        app.runSimulaion();

    }

}
