package src;
import java.util.concurrent.atomic.AtomicInteger;


public class StarvatingWorker implements Runnable {
    private AtomicInteger runCount = new AtomicInteger();
    static boolean isActive;
    static final  Object mutex = new Object();
    public void run() {
        while (isActive) {
            synchronized(mutex) {
                try {
                    doWork();
                } catch (Exception e) {
                    System.out.format("%s was interrupted...\n", Thread.currentThread().getName());
                    e.printStackTrace();
                }
            }
        }
        System.out.format("DONE===> %s: Current runCount is %d...\n", Thread.currentThread().getName(), runCount.get());
    }

    private void doWork() {
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        runCount.getAndIncrement();
    }
}
