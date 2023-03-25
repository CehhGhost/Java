public class Producer extends ProducerConsumer {
    public Producer(double[] buffer, int size) {
        super(buffer, size);
        ++index;
        System.out.println(index);
    }

    @Override
    public void run() {
        while (true) {
            synchronized (buffer) {
                if (index >= size) {
                    continue;
                }
                buffer[index] = Math.random() * 10;
                System.out.println("generated: " + buffer[index] + " index: " + index);
                ++index;
            }
            try {
                Thread.sleep(100 + (long) (Math.random() * 400));
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
