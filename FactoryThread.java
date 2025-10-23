
package GPRO2JAVA;

import java.util.*;
import java.util.concurrent.*;
/**
 *
 * @author iamja
 */
public class FactoryThread extends Thread{
    private int maxProduction;
    private int products;
    private CyclicBarrier barrier;
    private MyMonitor monitorS, monitorM;
    private int totalProducts;
    private int unshipped;
    private ArrayList<warehouse> warehouses;
    private ArrayList<freight> freights;
    private int days;
    private int allProduced;
    private int allShipped;
    public FactoryThread(String n, int d, int max)   {super(n); days = d; maxProduction = max;  }
    public void setMonitorS(MyMonitor m)   {monitorS = m;}
    public void setMonitorM(MyMonitor m)    {monitorM = m;}
    public void setBarrier(CyclicBarrier ba)    {barrier = ba;}
    public void setWarehouses(ArrayList<warehouse> w)   {warehouses = w;}
    public void setFreights(ArrayList<freight> f)   {freights = f;}
    @Override
    public void run()
    {
        while(true){
            monitorS.waitForThreads();
            Random rand = new Random();
            int selectw = rand.nextInt(0, warehouses.size()-1);
            int selectf = rand.nextInt(0, freights.size()-1);
            products = rand.nextInt(1, maxProduction);
            products = warehouses.get(selectw).get(products);
            getMats(selectw);
            allProduced += products;
            int c =-1;
            try{c = barrier.await();}catch(Exception e){}
            if(c==0)
            {
                System.out.println(Thread.currentThread().getName() + ">>");
            }
            totalProducts = unshipped + products;
            totalToShip();
            try{barrier.await();}catch(Exception e){}
            shippingProduct(selectf);
            try{barrier.await();}catch(Exception e){}
            unshippedProducts();
            days--;
            c = -1;
            try{c = barrier.await();}catch(Exception e){}
            if(c==0)
            {
                monitorM.wakeUpThreads();
            }
            if(days == 1){
                monitorM.waitForThreads();
                break;
            }
        }
            
        
        
        
            
        
    }
    public void getMats(int s)
    {
        System.out.printf("%s  >>  get %3d materials      %s balance = %5d\n",Thread.currentThread().getName(), products, warehouses.get(s).getName(), warehouses.get(s).getBalance());
    }
    public void totalToShip()
    {
        System.out.printf("%s  >>  total product to ship = %5d\n", Thread.currentThread().getName(), totalProducts);
    }
    public void shippingProduct(int s)
    {
        int shipped = freights.get(s).ship(totalProducts);
        int remain = totalProducts - shipped;
        allShipped += shipped;
        System.out.printf("%s  >>  ship %4d products      %s remaining capacity = %5d\n", Thread.currentThread().getName(), shipped, freights.get(s).getName(), freights.get(s).getRemaining());
        unshipped = remain;
    }
    public void unshippedProducts()
    {
        System.out.printf("%s  >>  unshipped products = %5d\n", Thread.currentThread().getName(), unshipped);
    }
}

