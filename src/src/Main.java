package src;
import src.Consumer;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;


public class Main {
    static boolean plato_Terminate = false;
    static boolean socrates_Terminate = false;
    public static void main(String[] args) {

        if (args[0].equals("deadlock")) {
            deadlock();
        }
        else if (args[0].equals("livelock")) {
            livelock();
        }
        else if (args[0].equals("starvation")) {
        }
    }
    static void deadlock(){
        ReentrantLock lockFork = new ReentrantLock();
        ReentrantLock lockKnife = new ReentrantLock();
        String resource1 = "Fork";
        String resource2 = "Knife";


        Thread Plato = new Thread(new Runnable() {

            public void run(){
                lockFork.lock();
                System.out.println("Plato: locked: " + resource1);
                try {
                    Thread.sleep(100);
                } catch (Exception e) {
                }
                while(lockKnife.isLocked()) {
                    if(plato_Terminate){
                        System.out.println("Plato terminated");
                        return;
                    }
                }
                lockKnife.lock();
                System.out.println("Plato: locked: " + resource2);
                lockFork.unlock();
                lockKnife.unlock();
            }
        });

        Thread Socrates = new Thread(new Runnable() {
            public void run(){
                lockKnife.lock();
                System.out.println("Socrates: locked: " + resource2);
                try {
                    Thread.sleep(100);
                } catch (Exception e) {
                }
                while(lockFork.isLocked()) {
                    if(socrates_Terminate == true){
                        System.out.println("Socrates terminated");
                        return;
                    }
                }
                lockFork.lock();
                System.out.println("Socrates: locked: " + resource2);
                lockFork.unlock();
                lockKnife.unlock();
            }
        });
        Plato.start();
        Socrates.start();
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


    }
    static void starvation(){}
}


