package src;
import src.Consumer;
public class Main {

    public static void main(String[] args) {

        if (args[0].equals("deadlock")) {
            deadlock();
        }
    }
    static void deadlock(){
            String resource1 = "Fork";
            String resource2 = "Knife";
            Thread Plato = new Thread(new Runnable() {
                public void run() {
                    synchronized (resource1) {
                        System.out.println("Plato: locked: " + resource1);
                        try {
                            Thread.sleep(100);
                        } catch (Exception e) {
                        }
                        synchronized (resource2) {
                            System.out.println("Plato: locked: " + resource2);
                        }
                    }
                }
            });
            Thread Socrates = new Thread(new Runnable() {
                public void run() {
                    synchronized (resource2) {
                        System.out.println("Socrates: locked: " + resource2);
                        try {
                            Thread.sleep(100);
                        } catch (Exception e) {
                        }
                        synchronized (resource1) {
                            System.out.println("Socrates: locked: " + resource1);
                        }
                    }
                }
            });
            Plato.start();
            Socrates.start();
        }
}

