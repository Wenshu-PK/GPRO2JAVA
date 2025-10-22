/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Project2;

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
    private ArrayList<WareHouse> warehouses;
    private ArrayList<Freight> freights;
    private int days;
    public FactoryThread(String n, int d, int max, ArrayList<WareHouse> w, ArrayList<Freight> f)   {super(n); days = d; maxProduction = max; warehouses = w; freights = f;}
    public void setMonitor(MyMonitor m)   {monitor = m;}
    public void setBarrier(CyclicBarrier ba)    {barrier = ba;}
    @Override
    public void run()
    {
        while(true){
            monitorS.waitForInput();
            Random rand = new Random();
            int selectw = rand.nextInt(0, warehouses.size()-1);
            int selectf = rand.nextInt(0, freights.size()-1);
            products = rand.nextInt(1, maxProduction);
            products = warehouses.get(selectw).get(products);
            getMats(selectw);
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
                break;
            }
        }
            
        
        
        
            
        
    }
    public void getMats(int s)
    {
        System.out.printf("%s  >>  get %3d materials      %s balance = %5d\n",Thread.currentThread().getName(), products, warehouses.get(s).getName(), warehouses.get(s).get());
    }
    public void totalToShip()
    {
        System.out.printf("%s  >>  total product to ship = %5d\n", Thread.currentThread().getName(), totalProducts);
    }
    public void shippingProduct(int s)
    {
        int remain = freights.get(s).put(totalProducts);
        int shipped = totalProducts - remain;
        System.out.printf("%s  >>  ship %4d products      %s remaining capacity = %5d\n", Thread.currentThread().getName(), shipped, freights.get(s).getName(), freights.get(s).get());
        unshipped = remain;
    }
    public void unshippedProducts()
    {
        System.out.printf("%s  >>  unshipped products = %5d\n", Thread.currentThread().getName(), unshipped);
    }
}
