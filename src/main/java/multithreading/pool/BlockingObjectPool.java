package multithreading.pool;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.LinkedList;
import java.util.Queue;
@Data
@RequiredArgsConstructor
public class BlockingObjectPool {
    private final Queue<Object> queue = new LinkedList<>();
    private final int maxSize;
    private final Object FULL_QUEUE = new Object();
    private final Object EMPTY_QUEUE = new Object();

    public boolean isFull() {
        return queue.size() == maxSize;
    }

    public boolean isEmpty() {
        return queue.isEmpty();
    }

    public void waitOnFull() throws InterruptedException {
        synchronized (FULL_QUEUE) {
            FULL_QUEUE.wait();
        }
    }

    public void waitOnEmpty() throws InterruptedException {
        synchronized (EMPTY_QUEUE) {
            EMPTY_QUEUE.wait();
        }
    }

    public void notifyAllForFull() {
        synchronized (FULL_QUEUE) {
            FULL_QUEUE.notifyAll();
        }
    }

    public void notifyAllForEmpty() {
        synchronized (EMPTY_QUEUE) {
            EMPTY_QUEUE.notifyAll();
        }
    }

    public void add(Object o) throws InterruptedException {
        if (isFull())
            notifyAllForFull();
            waitOnFull();
        synchronized (queue) {
            queue.add(o);
        }
    }

    public Object peek() throws InterruptedException {
        if(isEmpty())
            notifyAllForEmpty();
            waitOnEmpty();
        synchronized (queue) {
            return queue.peek();
        }
    }
}
