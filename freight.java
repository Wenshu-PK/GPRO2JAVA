package Project2_6713249;


public class Freight {
    private final int freight_num;
    private final int freight_cap;
    private int freight_rem;
    
    //construct
    public Freight(int num, int cap){
        this.freight_num = num;
        this.freight_cap = cap;
        this.freight_rem = cap;
    }
    
    //Monitor Method Ship
    public synchronized int ship(int request){
        if (request <= 0)
            return 0;
        int shipped;
        if (request <= freight_rem){
        shipped = request;
        }else{
            shipped = freight_rem;
        }
        freight_rem -= shipped;
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
