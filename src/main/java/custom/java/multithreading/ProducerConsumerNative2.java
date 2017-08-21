package custom.java.multithreading;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ProducerConsumerNative2 {
	
	private Object lock = new Object();
	private List<Integer> buffer = new ArrayList<Integer>();
	private final int LIMIT = 10;
	private int count = 1;

	public void procuder() {
		// TODO Auto-generated method stub
		while (true) {
			synchronized (this.buffer) {
				try {
					System.out.println("Producer started ... ");
					while (buffer.size() == LIMIT) {
						buffer.wait();
					}
					buffer.add(count);
					System.out.println("Data Pushed ::: " + count++);
					buffer.notify();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}

	public void consumer() {
		// TODO Auto-generated method stub
		try {
			Thread.sleep(3000);

			while (true) {
				synchronized (this.buffer) {
					System.out.println("Consumer started ... ");
					while (buffer.isEmpty()) {
						System.out.println("Buffer is empty.");
						buffer.wait();
					}
					System.out.print("Buffer Size : " + buffer.size());
					System.out.println("\t\t\tData Removed : " + buffer.remove(0));
					buffer.notify();
				}
				Thread.sleep(new Random().nextInt(500));
			}
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}

	public static void main(String[] args) {
		Thread producer = new Thread(new Runnable() {

			public void run() {
				// TODO Auto-generated method stub
				ProducerConsumer producerConsumer = new ProducerConsumerNative();
				producerConsumer.procuder();
			}
		});

		Thread consumer = new Thread(new Runnable() {

			public void run() {
				// TODO Auto-generated method stub
				ProducerConsumer producerConsumer = new ProducerConsumerNative();
				producerConsumer.consumer();
			}
		});

		producer.start();
		consumer.start();

		try {
			producer.join();
			consumer.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
