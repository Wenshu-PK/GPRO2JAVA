/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Project2;

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
