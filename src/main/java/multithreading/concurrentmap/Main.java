package multithreading.concurrentmap;

import static java.lang.Thread.sleep;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;

public class Main {
    private static Map<Integer, Integer> integerMap;

    public static void main(String[] args) throws InterruptedException {
        integerMap = new ConcurrentHashMap<>();
        Thread t2 = new MyThread2();
        Thread t1 = new MyThread1();
        t1.start();
        t2.start();
//        for (int i = 0; i < 10; i++) {
//            Thread t1 = new MyThread1();
//            t1.start();
//        }
        sleep(5000);
        int sum = integerMap.values().stream().mapToInt(i -> i).sum();
        int sum1 = MyCustomConcurrentMap.sumValue();
        System.out.println("Final sum: " + sum);
        System.out.println("Custom Final sum: " + sum1);

    }

    public static class MyThread1 extends Thread {
        @Override
        public void run() {
            addValue();
        }

        public void addValue() {
            Random random = new Random();
            int it = 500000;
            for (int i = 0; i < it; i++) {
                int value = random.nextInt(1, 100);
                integerMap.put(i, value);
                MyCustomConcurrentMap.addValue(i, value);
            }
            System.out.println("latest element: " + integerMap.get(it - 1));
            System.out.println("latest element of Custom map: " + integerMap.get(it - 1));
        }
    }

    public static class MyThread2 extends Thread {
        @Override
        public void run() {
            try {
                sumValue();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        public void sumValue() throws InterruptedException {
            sleep(1);
            int sum = integerMap.values().stream().mapToInt(i -> i).sum();
            int sum1 = MyCustomConcurrentMap.sumValue();
            System.out.println("sum: " + sum);
            System.out.println("My custom sum : " + sum1);
        }
    }

    public static final class MyCustomConcurrentMap {
        private static volatile Map<Integer, Integer> instance;

        private MyCustomConcurrentMap() {
            // to prevent entity creation
        }

        public static synchronized Map<Integer, Integer> getMap() {
            if (instance == null) {
                synchronized (MyCustomConcurrentMap.class) {
                    instance = new HashMap<>();
                }
                return instance;
            }
            synchronized (MyCustomConcurrentMap.class) {
                return instance;
            }
        }

        public static synchronized Map<Integer, Integer> addValue(Integer key, Integer value) {
            getMap().put(key, value);
            return getMap();
        }

        public static synchronized Integer sumValue() {
            return getMap().values().stream().mapToInt(i -> i).sum();
        }
    }
}