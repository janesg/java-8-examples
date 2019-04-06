package com.devxpress;

import java.util.Random;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.SynchronousQueue;

public class ProducerConsumer {
    public static void main(String[] args) {
        ProducerConsumer pc = new ProducerConsumer();
        pc.runExample();
    }
    
    private void runExample() {
        // BlockingQueue<String> bq = new SynchronousQueue<String>();
        BlockingQueue<String> bq = new ArrayBlockingQueue<String>(2);

        (new Thread(new ProducerUsingBlockingQueue(bq))).start();
        (new Thread(new ConsumerUsingBlockingQueue(bq))).start();
    }
    
    class ProducerUsingBlockingQueue implements Runnable {
        private BlockingQueue<String> bq;
    
        public ProducerUsingBlockingQueue(BlockingQueue<String> bq) {
            this.bq = bq;
        }
    
        @Override
        public void run() {
            String importantInfo[] = {
                "Mares eat oats",
                "Does eat oats",
                "Little lambs eat ivy",
                "A kid will eat ivy too"
            };
            
            Random random = new Random();
    
            for (int i = 0; i < importantInfo.length; i++) {
                try {
                    bq.put(importantInfo[i]);
                    Thread.sleep(random.nextInt(2000));
                } catch (InterruptedException e) {
                }
            }
            
            try {
                bq.put("DONE");
            } catch (InterruptedException e) {
            }
        }
    }
    
    class ConsumerUsingBlockingQueue implements Runnable {
        private BlockingQueue<String> bq;
    
        public ConsumerUsingBlockingQueue(BlockingQueue<String> bq) {
            this.bq = bq;
        }
    
        @Override
        public void run() {
            Random random = new Random();
            
            try {
                String message = null;
                
                do {
                    message = bq.take();
                    System.out.format("MESSAGE RECEIVED: %s%n", message);
                    // Thread.sleep(random.nextInt(1000));
                } while (!message.equals("DONE"));
            } catch (InterruptedException e) {
            }
        }
    }    
}