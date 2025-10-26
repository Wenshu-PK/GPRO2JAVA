package GPRO2JAVA;


public class freight {
    private final int freight_num;
    private final int freight_cap;
    private int freight_rem;
    
    //construct
    public freight(int num, int cap){
        this.freight_num = num;
        this.freight_cap = cap;
        this.freight_rem = cap;
    }
    
    //Monitor Method Ship
    public synchronized int ship(int request){
        if (request <= 0)
            return 0;
        int shipped = Math.min(request, freight_rem);
        freight_rem -= shipped;
        System.out.printf("%s  >>  ship %4d products      %s remaining capacity = %5d\n", Thread.currentThread().getName(), shipped, getName(), this.freight_rem);
        return shipped;
    }

    //Monitor Method Reset
    public synchronized void reset() {
        freight_rem = freight_cap;
    }

    // Helpers
    public synchronized int getRemaining() { return freight_rem; }
    public int getCapacity() { return freight_cap; }
    public String getName() { return "Freight-" + freight_num; }

    @Override
    public synchronized String toString() {
        return "Freight-" + freight_num + " [" + freight_rem + "/" + freight_cap + "]";
    }
}
