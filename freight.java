package Project2_6713249;

//Anun Luechaphongthip         6713253
//Puvit Kitiwongpaisan         6713246
//Kanapod Lamthong             6713220
//Piyawat Jaroonchaikhanakit   6713240
//Sawana Thiputhai             6713249
    
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
        int shipped;
        if (request <= freight_rem){
        shipped = request;
        }else{
            shipped = freight_rem;
        }
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
