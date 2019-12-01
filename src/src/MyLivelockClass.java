package src;
/**
 * Police.java
 * This class is used to demonstrate livelock situation
 * @author www.codejava.net
 */
public class MyLivelockClass {
    private boolean operationFinished = false;
    private Object name;
    public MyLivelockClass(String name){
        this.name = name;
    }

    public void func(MyLivelockClass A_rep) {

        while (!A_rep.isOperationFinished()) {

            System.out.println(name + ": waiting ");

            try {
                Thread.sleep(1000);
            } catch (InterruptedException ex) {
                System.out.println(this.name + ": interrupted");
                this.operationFinished = true;
                return;
            }
        }

        System.out.println(this.name +": finished");

        this.operationFinished = true;
    }

    public boolean isOperationFinished() {
        return this.operationFinished;
    }

}
