public class ProducerConsumer extends Thread {
    protected double[] buffer;
    protected int size;
    static protected int index = -1;
    public ProducerConsumer(double[] buffer, int size) {
        this.buffer = buffer;
        this.size = size;
    }
}
