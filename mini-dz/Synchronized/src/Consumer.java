import java.util.concurrent.Semaphore;

public class Consumer extends ProducerConsumer {
    public Consumer(double[] buffer, int size) {
        super(buffer, size);
    }

    @Override
    public void run() {
        while (true) {
            synchronized (buffer) {
                if (index <= 0) {
                    continue;
                }
                --index;
                System.out.println("consumed: " + buffer[index] + " index: " + index);
            }
            try {
                Thread.sleep(100 + (long) (Math.random() * 400));
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
