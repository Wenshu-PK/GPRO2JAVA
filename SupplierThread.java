/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Project2;

import java.util.*;


/**
 *
 * @author puvit
 */
public class SupplierThread extends Thread {
    private final List<Warehouse> warehouses;
    private final int minSupply;
    private final int maxSupply;
    private final int days;
    private final DaySynchronizer sync;
    private final Random rng = new Random();

    public SupplierThread(String name, List<Warehouse> warehouses,
                          int minSupply, int maxSupply, int days, DaySynchronizer sync) {
        super(name);
        this.warehouses = warehouses;
        this.minSupply = minSupply;
        this.maxSupply = maxSupply;
        this.days = days;
        this.sync = sync;
    }

    @Override
    public void run() {
        for (int day = 1; day <= days; day++) {
            try {
                sync.awaitStart();

                int amount = minSupply + rng.nextInt(maxSupply - minSupply + 1);
                Warehouse w = warehouses.get(rng.nextInt(warehouses.size()));
                w.put(amount);
                System.out.printf("%s >> put %d materials\t %s balance = %d%n",
                        getName(), amount, w.getName(), w.getBalance());

                sync.awaitAfterSuppliers();
                sync.awaitAfterShipping();
            } catch (Exception e) {
                System.out.printf("%s >> interrupted/broken at day %d%n", getName(), day);
                return;
            }
        }
    }
}

