package multithreading.threethreads;

import java.util.Map;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;

public class MainThreads {
    private static Map<Integer, Integer> integerMap;

    public static void main(String[] args) {
        integerMap = new ConcurrentHashMap<>();
        Thread t2 = new MyThread2();
        Thread t3 = new MyThread3();
        Thread t1 = new MyThread1();
        t1.start();
        t2.start();
        t3.start();


    }

    public static class MyThread1 extends Thread {
        @Override
        public void run() {
            addValue();
        }

        public void addValue() {
            Random random = new Random();
            int it = 500000;
            for (; ; ) {
                int value1 = random.nextInt(1, 10);
                int value = random.nextInt(1, 10);
                integerMap.put(value, value1);
            }
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
            while (true) {
                System.out.println("sum: " + integerMap.values().stream().mapToInt(i -> i).sum());
            }
        }
    }

    public static class MyThread3 extends Thread {
        @Override
        public void run() {
            try {
                sumValue();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        public void sumValue() throws InterruptedException {
            while (true) {
                int sum = integerMap.values().stream().mapToInt(i -> (int) Math.pow(i, 2)).sum();
                System.out.println("sum squares: " + Math.sqrt(sum));
            }
        }
    }

}