package src;

import java.util.concurrent.locks.ReentrantLock;

public class Consumer implements Runnable{
    private String name = "";
    public boolean Terminate = false;
    ReentrantLock lock1;
    ReentrantLock lock2;
    String resource1;
    String resource2;
    public Consumer(String name, ReentrantLock lock1,ReentrantLock lock2, String resource1, String resource2){
        this.name = name;
        this.lock1 = lock1;
        this.lock2 = lock2;
        this.resource1 = resource1;
        this.resource2 = resource2;
        this.Terminate = false;
    }
    public void run(){
        lock1.lock();

        System.out.println(name + " locked: " + resource1);
        try {
            Thread.sleep(1000);
        } catch (Exception e) {
        }
        while(lock2.isLocked()) {
            if(Terminate){
                System.out.println(name + "terminated");
                return;
            }
        }

        lock2.lock();
        System.out.println(name + ": locked: " + resource2);
        lock1.unlock();
        lock2.unlock();
    }

    public void setTerminate(boolean terminate) {
        Terminate = terminate;
    }

}