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
    private MyMonitor monitor;
    private int totalProduction;
    private int unshipped;
    private ArrayList<WareHouse> warehouses;
    private ArrayList<Freight> freights;
    public FactoryThread(int max, ArrayList<WareHouse> w, ArrayList<Freight> f)   {maxProduction = max; warehouses = w; freights = f;}
    public void setMonitor(MyMonitor m)   {monitor = m;}
    public void setBarrier(CyclicBarrier ba)    {barrier = ba;}
    @Override
    public void run()
    {
        Random rand = new Random();
        int selectw = rand.nextInt(0, warehouses.size()-1);
        int selectf = rand.nextInt(0, freights.size()-1);
        products = rand.nextInt(1, maxProduction);
        products = warehouses.get(selectw).get(products);
        
            
        
        
        
            
        
    }
    
}
