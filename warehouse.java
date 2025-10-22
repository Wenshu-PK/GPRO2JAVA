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
public class Warehouse {
    private final String name;
    private int balance = 0;

    public Warehouse(String name) {
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
