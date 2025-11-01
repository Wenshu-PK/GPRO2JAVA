package Project2_6713249;

//Anun Luechaphongthip         6713253
//Puvit Kitiwongpaisan         6713246
//Kanapod Lamthong             6713220
//Piyawat Jaroonchaikhanakit   6713240
//Sawana Thiputhai             6713249
    
import java.util.*;

/**
 *
 * @author sawan
 */
public class config {

    private int days;
    private int warehouse_num;
    private int freight_num;
    private int freight_num_max;
    private int supplier_num;
    private int supplier_num_min;
    private int supplier_num_max;
    private int factory_num;
    private int factory_num_max;

    public config(ArrayList<Integer> data) {
        if (data.size() != 9) {
            throw new IllegalArgumentException("Config data must have at least 8 integers");
        }

        this.days = data.get(0);
        this.warehouse_num = data.get(1);
        this.freight_num = data.get(2);
        this.freight_num_max = data.get(3);
        this.supplier_num = data.get(4);
        this.supplier_num_min = data.get(5);
        this.supplier_num_max = data.get(6);
        this.factory_num = data.get(7);
        this.factory_num_max = data.get(8);

    }

    public int getDay() {
        return this.days;

    }

    public int getWarehouse() {
        return this.warehouse_num;

    }

    public int[] getFreight() {
        int[] freightDetail = new int[] { this.freight_num, this.freight_num_max };
        return freightDetail;

    }

    public int[] getSupplier() {
        int[] supplierDetail = new int[] { this.supplier_num, this.supplier_num_min, this.supplier_num_max };
        return supplierDetail;
    }

    public int[] getFactory() {
         int[] factoryDetail = new int[] {this.factory_num,this.factory_num_max};
        return factoryDetail;
    }

    public int getNumThread() {
        return this.factory_num + this.supplier_num;
    }
}


