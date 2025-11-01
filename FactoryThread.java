package Project2_6713249

//Anun Luechaphongthip         6713253
//Puvit Kitiwongpaisan         6713246
//Kanapod Lamthong             6713220
//Piyawat Jaroonchaikhanakit   6713240
//Sawana Thiputhai             6713249
    
import java.util.*;
import java.util.concurrent.*;

/**
 *
 * @author iamja
 */
public class FactoryThread extends Thread {

    private int maxProduction;
    private int products;
    private CyclicBarrier barrier;
    private MyMonitor monitorF, monitorM;
    private int totalProducts;
    private int unshipped;
    private ArrayList<warehouse> warehouses;
    private ArrayList<freight> freights;
    private int days;
    private int allProduced;
    private int allShipped;

    public FactoryThread(String n, int d, int max) {
        super(n);
        this.days = d;
        this.maxProduction = max;
    }

    public void setMonitorF(MyMonitor m) {
        this.monitorF = m;
    }

    public void setMonitorM(MyMonitor m) {
        this.monitorM = m;
    }

    public void setBarrier(CyclicBarrier ba) {
        this.barrier = ba;
    }

    public void setWarehouses(ArrayList<warehouse> w) {
        this.warehouses = w;
    }

    public void setFreights(ArrayList<freight> f) {
        this.freights = f;
    }

    @Override
    public void run() {
        while (true) {
            monitorF.waitForThreads();
            Random rand = new Random();
            int selectw = rand.nextInt( warehouses.size());
            int selectf = rand.nextInt( freights.size());
            products = maxProduction;
            products = warehouses.get(selectw).get(products);
            //getMats(selectw);
            allProduced += products;
            int c = -1;
            try {
                c = barrier.await();
            } catch (Exception e) {
            }
            if (c == 0) {
                System.out.println(Thread.currentThread().getName() + "  >>");
            }
            try {
                c = barrier.await();
            } catch (Exception e) {
            }
            totalProducts = unshipped + products;
            totalToShip();
            try {
                barrier.await();
            } catch (Exception e) {
            }
            shippingProduct(selectf);
            try {
                barrier.await();
            } catch (Exception e) {
            }
            unshippedProducts();
            days--;
            c = -1;
            try {
                c = barrier.await();
            } catch (Exception e) {
            }
            if (c == 0) {
                monitorM.wakeUpThreads();
            }
            if (days == 0) {
                
                break;
            }
        }

    }

    /*public void getMats(int s) {
        System.out.printf("%s  >>  get %3d materials      %s balance = %5d\n", Thread.currentThread().getName(), products, warehouses.get(s).getName(), warehouses.get(s).getBalance());
    }*/

    public synchronized void totalToShip() {
        System.out.printf("%s  >>  total product to ship = %5d\n", Thread.currentThread().getName(), totalProducts);
    }

    public void shippingProduct(int s) {
        int shipped = freights.get(s).ship(totalProducts);
        int remain = totalProducts - shipped;
        allShipped += shipped;
        //System.out.printf("%s  >>  ship %4d products      %s remaining capacity = %5d\n", Thread.currentThread().getName(), shipped, freights.get(s).getName(), freights.get(s).getRemaining());
        unshipped = remain;
    }

    public synchronized void unshippedProducts() {
        System.out.printf("%s  >>  unshipped products = %5d\n", Thread.currentThread().getName(), unshipped);
    }
    public void printSummary(){
        double p = allProduced;
        double s = allShipped;
        double percent = ( s / p ) * 100.0;
        System.out.printf("            %s >>  %s    total products = %4d   shipped = %4d ( %4.2f )\n", Thread.currentThread().getName(), this.getName(), allProduced, allShipped, percent);
    }
}


