package Project2_6713249;

//Anun Luechaphongthip         6713253
//Puvit Kitiwongpaisan         6713246
//Kanapod Lamthong             6713220
//Piyawat Jaroonchaikhanakit   6713240
//Sawana Thiputhai             6713249

import java.util.*;

/**
 * Warehouse matches the API expected by SupplierThread example:
 * - constructor Warehouse(String name)
 * - synchronized put(int amount)
 * - synchronized int get(int maxNeeded)
 * - synchronized int getBalance()
 * - String getName()
 *
 * NOTE: put/get do not print anything to avoid duplicate prints;
 *       caller threads (SupplierThread / FactoryThread) should print.
 */
public class warehouse {
    private final String name;
    private int balance = 0;

    public warehouse(String name) {
        this.name = name;
    }

    // add amount to balance (thread-safe)
    public synchronized void put(int amount) {
        if (amount <= 0) return;
        this.balance += amount;
    }

    // attempt to take up to maxNeeded; return actual taken (thread-safe)
    public synchronized int get(int maxNeeded) {
        if (maxNeeded <= 0) return 0;
        int taken = Math.min(maxNeeded, this.balance);
        this.balance -= taken;
        System.out.printf("%s  >>  get %3d materials      %s balance = %5d\n", Thread.currentThread().getName(), taken, this.name, this.balance);
        return taken;
    }

    // read-only balance (thread-safe)
    public synchronized int getBalance() {
        return this.balance;
    }

    public String getName() {
        return this.name;
    }
    
}
