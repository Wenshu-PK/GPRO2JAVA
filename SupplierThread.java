
package GPRO2JAVA;

import java.util.*;
import java.util.concurrent.*;


/**
 *
 * @author puvit
 */
public class SupplierThread extends Thread {
    private ArrayList<warehouse> warehouses;
    private int minSupply;
    private int maxSupply;
    private int days;
    private CyclicBarrier barrier;
    private MyMonitor monitorS, monitorF;
    private Random rng = new Random();
    public void setMonitorS(MyMonitor m)   {monitorS = m;}
    public void setMonitorF(MyMonitor m)    {monitorF = m;}
    public void setBarrier(CyclicBarrier ba)    {barrier = ba;}
    
    

    public SupplierThread(String name, ArrayList<warehouse> warehouses,
                          int minSupply, int maxSupply, int days ) {
        super(name);
        this.warehouses = warehouses;
        this.minSupply = minSupply;
        this.maxSupply = maxSupply;
        this.days = days;
        
        
    }

    @Override
    public void run() {
        for (int day = 1; day <= days; day++) {
            try {
                monitorS.waitForThreads();
//
                int amount = minSupply + rng.nextInt(maxSupply - minSupply + 1);
                warehouse w = warehouses.get(rng.nextInt(warehouses.size()));
                w.put(amount);
                System.out.printf("%s >> put %d materials\t %s balance = %d%n",
                        getName(), amount, w.getName(), w.getBalance());
//
                int c = -1;
                try{c = barrier.await();}catch(Exception e){}
                if(c==0)
                {
                    monitorF.wakeUpThreads();
                }
            } catch (Exception e) {
                System.out.printf("%s >> interrupted/broken at day %d%n", getName(), day);
                return;
            }
        }
    }
}
