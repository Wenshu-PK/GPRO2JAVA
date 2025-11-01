package Project2_6713249

//Anun Luechaphongthip         6713253
//Puvit Kitiwongpaisan         6713246
//Kanapod Lamthong             6713220
//Piyawat Jaroonchaikhanakit   6713240
//Sawana Thiputhai             6713249
    
/**
 *
 * @author iamja
 */
public class MyMonitor {
    
     public synchronized void waitForThreads()
    {
            try{wait();} catch(Exception e){}
    }
    public synchronized void wakeUpThreads(){
            notifyAll();
    }
}

