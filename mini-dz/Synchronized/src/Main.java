public class Main {
    public static void main(String[] args) {
        int size = 10;
        double[] mass = new double[size];
        Thread pr = new Producer(mass , size);
        Thread con = new Consumer(mass , size);
        pr.start();
        con.start();
    }
}