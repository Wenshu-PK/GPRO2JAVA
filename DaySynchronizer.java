/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Project2;

import java.util.concurrent.*;

/**
 *
 * @author puvit
 */
public class DaySynchronizer {
    private final CyclicBarrier barrierStart;
    private final CyclicBarrier barrierAfterSuppliers;
    private final CyclicBarrier barrierAfterShipping;

    public DaySynchronizer(int totalThreadsPlusMain) {
        barrierStart = new CyclicBarrier(totalThreadsPlusMain);
        barrierAfterSuppliers = new CyclicBarrier(totalThreadsPlusMain);
        barrierAfterShipping = new CyclicBarrier(totalThreadsPlusMain);
    }

    public void awaitStart() throws InterruptedException, BrokenBarrierException {
        barrierStart.await();
    }

    public void awaitAfterSuppliers() throws InterruptedException, BrokenBarrierException {
        barrierAfterSuppliers.await();
    }

    public void awaitAfterShipping() throws InterruptedException, BrokenBarrierException {
        barrierAfterShipping.await();
    }
}
