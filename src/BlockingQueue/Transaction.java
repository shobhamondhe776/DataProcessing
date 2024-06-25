package BlockingQueue;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.locks.ReentrantLock;

public class Transaction {
    private final String id;
    private final long timestamp;
    private final String type;
    private final double amount;

    public Transaction(String id, long timestamp, String type, double amount) {
        this.id = id;
        this.timestamp = timestamp;
        this.type = type;
        this.amount = amount;
    }

    // Getters and toString() method
}
class TransactionHistory implements List<Transaction> {
    private final List<Transaction> history = new ArrayList<>();

    @Override
    public synchronized boolean add(Transaction transaction) {
        return history.add(transaction);
    }

    @Override
    public synchronized Transaction get(int index) {
        return history.get(index);
    }

    @Override
    public synchronized boolean remove(Object o) {
        return history.remove(o);
    }

    @Override
    public synchronized Iterator<Transaction> iterator() {
        return history.iterator();
    }

    @Override
    public synchronized int size() {
        return history.size();
    }

    @Override
    public synchronized boolean isEmpty() {
        return history.isEmpty();
    }

    @Override
    public synchronized boolean contains(Object o) {
        return history.contains(o);
    }

    @Override
    public synchronized Object[] toArray() {
        return history.toArray();
    }

    @Override
    public synchronized <T> T[] toArray(T[] a) {
        return history.toArray(a);
    }

    @Override
    public synchronized boolean containsAll(Collection<?> c) {
        return history.containsAll(c);
    }

    @Override
    public synchronized boolean addAll(Collection<? extends Transaction> c) {
        return history.addAll(c);
    }

    @Override
    public synchronized boolean addAll(int index, Collection<? extends Transaction> c) {
        return history.addAll(index, c);
    }

    @Override
    public synchronized boolean removeAll(Collection<?> c) {
        return history.removeAll(c);
    }

    @Override
    public synchronized boolean retainAll(Collection<?> c) {
        return history.retainAll(c);
    }

    @Override
    public synchronized void clear() {
        history.clear();
    }

    @Override
    public synchronized Transaction set(int index, Transaction element) {
        return history.set(index, element);
    }

    @Override
    public synchronized void add(int index, Transaction element) {
        history.add(index, element);
    }

    @Override
    public synchronized Transaction remove(int index) {
        return history.remove(index);
    }

    @Override
    public synchronized int indexOf(Object o) {
        return history.indexOf(o);
    }

    @Override
    public synchronized int lastIndexOf(Object o) {
        return history.lastIndexOf(o);
    }

    @Override
    public synchronized ListIterator<Transaction> listIterator() {
        return history.listIterator();
    }

    @Override
    public synchronized ListIterator<Transaction> listIterator(int index) {
        return history.listIterator(index);
    }

    @Override
    public synchronized List<Transaction> subList(int fromIndex, int toIndex) {
        return history.subList(fromIndex, toIndex);
    }
}   class TransactionProcessor {
	    private static final int PRODUCER_COUNT = 5;
	    private static final int CONSUMER_COUNT = 10;

	    public static void main(String[] args) {
	        BlockingQueue<Transaction> queue = new LinkedBlockingQueue<>();
	        TransactionHistory history = new TransactionHistory();

	        // Start producer threads
	        for (int i = 0; i < PRODUCER_COUNT; i++) {
	            new Thread(new Producer(queue)).start();
	        }

	        // Start consumer threads
	        for (int i = 0; i < CONSUMER_COUNT; i++) {
	            new Thread(new Consumer(queue, history)).start();
	        }
	    }

	    private static class Producer implements Runnable {
	        private final BlockingQueue<Transaction> queue;

	        Producer(BlockingQueue<Transaction> queue) {
	            this.queue = queue;
	        }

	        @Override
	        public void run() {
	            while (true) {
	                try {
	                    Transaction transaction = generateRandomTransaction();
	                    queue.put(transaction);
	                    System.out.println("Produced: " + transaction);
	                } catch (InterruptedException e) {
	                    Thread.currentThread().interrupt();
	                }
	            }
	        }
	    }

	    private static class Consumer implements Runnable {
	        private final BlockingQueue<Transaction> queue;
	        private final TransactionHistory history;

	        Consumer(BlockingQueue<Transaction> queue, TransactionHistory history) {
	            this.queue = queue;
	            this.history = history;
	        }

	        @Override
	        public void run() {
	            while (true) {
	                try {
	                    Transaction transaction = queue.take();
	                    history.add(transaction);
	                    System.out.println("Consumed: " + transaction);
	                } catch (InterruptedException e) {
	                    Thread.currentThread().interrupt();
	                }
	            }
	        }
	    }

    private static final String[] TRANSACTION_TYPES = {"BUY", "SELL", "DEPOSIT", "WITHDRAWAL"};
    private static final Random RANDOM = new Random();

    private static Transaction generateRandomTransaction() {
        String id = UUID.randomUUID().toString();
        long timestamp = System.currentTimeMillis();
        String type = TRANSACTION_TYPES[RANDOM.nextInt(TRANSACTION_TYPES.length)];
        double amount = generateRandomAmount();
        return new Transaction(id, timestamp, type, amount);
    }

    private static double generateRandomAmount() {
        double minAmount = 100.0;
        double maxAmount = 10000.0;
        return minAmount + (maxAmount - minAmount) * RANDOM.nextDouble();
    }
 }
