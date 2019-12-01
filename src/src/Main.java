package src;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;


public class Main {
    private static boolean plato_Terminate = false;
    private static boolean socrates_Terminate = false;
    public static void main(String[] args) {

        if (args[0].equals("deadlock")) {
            deadlock();
        }
        else if (args[0].equals("livelock")) {
            livelock();
        }
        else if (args[0].equals("starvation")) {
            starvation();
        }
    }
    static void deadlock(){
        ReentrantLock lockFork = new ReentrantLock();
        ReentrantLock lockKnife = new ReentrantLock();
        Object resource1 = "Fork";
        Object resource2 = "Knife";

        Thread Thread1 = new Thread(new Runnable() {
            public void run(){
                lockKnife.lock();
                System.out.println("Thread1: locked: " + resource2);
                try {
                    Thread.sleep(100);
                } catch (Exception e) {
                }
                while (lockFork.isLocked()) {
                    if (socrates_Terminate == true) {
                        System.out.println("Thread1 terminated");
                        return;
                    }
                }
                lockFork.lock();
                System.out.println("Thread1: locked: " + resource1);
            }
        });

        Thread Thread2 = new Thread(new Runnable() {
            public void run(){
                lockFork.lock();
                System.out.println("Thread2: locked: " + resource1);
                try {
                    Thread.sleep(100);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                while (lockKnife.isLocked()) {
                    if (plato_Terminate) {
                        System.out.println("Thread2 terminated");
                        return;
                    }
                }
                lockKnife.lock();
                System.out.println("Thread2: locked: " + resource2);
            }
        });

        Thread2.start();
        Thread1.start();
        try{
            TimeUnit.MILLISECONDS.sleep(2000);
        }
        catch(InterruptedException i){
            i.printStackTrace();
        }
        socrates_Terminate = true;
        plato_Terminate = true;
    }
    static void livelock(){
        MyLivelockClass A = new MyLivelockClass("buono");
        MyLivelockClass B = new MyLivelockClass("brutto");
        MyLivelockClass C = new MyLivelockClass("cattivo");

        Thread t1 = new Thread(new Runnable() {
            public void run() {
                A.func(B);
            }
        });
        t1.start();

        Thread t2 = new Thread(new Runnable() {
            public void run() {
                B.func(A);
            }
        });
        t2.start();

        Thread t3 = new Thread(new Runnable() {
            public void run() {
                C.func(B);
            }
        });
        t3.start();

        try{
            TimeUnit.MILLISECONDS.sleep(3000);
        }
        catch(InterruptedException i){
            i.printStackTrace();
        }
        t1.interrupt();

    }

    static void starvation(){
        StarvatingWorker.isActive = true;
        Thread t1 = new Thread(new StarvatingWorker(), "Thread_1_P10");
        Thread t2 = new Thread(new StarvatingWorker(), "Thread_2_P8");
        Thread t3 = new Thread(new StarvatingWorker(), "Thread_3_P6");
        Thread t4 = new Thread(new StarvatingWorker(), "Thread_4_P4");
        Thread t5 = new Thread(new StarvatingWorker(), "Thread_5_P2");

        // Priorities only serve as hints to scheduler, it is up to OS implementation to decide
        t1.setPriority(10);
        t2.setPriority(8);
        t3.setPriority(6);
        t4.setPriority(4);
        t5.setPriority(2);

        t1.start();
        t2.start();
        t3.start();
        t4.start();
        t5.start();

        //  Make the Main Thread sleep for 5 seconds
        //  then set isActive to false to stop all threads
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        StarvatingWorker.isActive = false;

    }
}


