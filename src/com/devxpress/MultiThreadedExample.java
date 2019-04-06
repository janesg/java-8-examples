package com.devxpress;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.concurrent.locks.StampedLock;

import java.util.stream.IntStream;

public class MultiThreadedExample {
    
    public static void main(String[] args) {
        
        MultiThreadedExample mte = new MultiThreadedExample();
        
        // mte.threadBasics();
        // mte.basicExecutor();
        // mte.basicCallablesFutures();
        // mte.invokeCallablesFutures();
        // mte.scheduledExecutor();
        // mte.synchronization();
        // mte.reentrantLocking();
        // mte.readWriteLocking();
        // mte.stampedLocking();
        mte.multiThreadedPrint();
    }
    
    private void threadBasics() {
        
        // Define run() method of @FunctionalInterface Runnable using a lambda
        Runnable rTask = () -> System.out.println("Hello from " + Thread.currentThread().getName());

        // Run task in main thread
        rTask.run();
        
        // Run task in separate thread
        new Thread(rTask).start();
        
        System.out.println("Done runnin' those tasks !");
        
        new Thread(() -> {
                try {
                    String threadName = Thread.currentThread().getName();
                    System.out.println("Foo : " + threadName);
                    TimeUnit.SECONDS.sleep(1);
                    System.out.println("Bar : " + threadName);
                } catch (InterruptedException e) {
                    System.out.println("Task interrupted : " + e.getMessage());
                }
            }
        ).start();
    }
    
    private void basicExecutor() {
        
        ExecutorService exec = Executors.newSingleThreadExecutor();
        exec.submit(() -> System.out.println("Executor says 'Hello' from " + Thread.currentThread().getName()));
        
        // Unless we stop it Executor will continue to run
        stopExecutor(exec);
    }
    
    private void basicCallablesFutures() {
        
        // Create a callable...like a runnable but can return a value
        Callable<Integer> cTask = () -> {
            try {
                TimeUnit.SECONDS.sleep(2);
                return 123;
            } catch (InterruptedException e) {
                // If I just do a println here, compiler chokes with following:
                // error: incompatible types: bad return type in lambda expression
                // System.out.println("Task interrupted : " + e.getMessage());
                throw new RuntimeException("Task interrupted : " + e.getMessage());
            }
        };
        
        ExecutorService exec = Executors.newFixedThreadPool(1);
        Future<Integer> futureResult = exec.submit(cTask);
        
        try {
            System.out.println("Future is done ? : " + futureResult.isDone());
            // Pass timeout so that 'get' doesn't wait forever
            Integer result = futureResult.get(3, TimeUnit.SECONDS);
            System.out.println("Future is done ? : " + futureResult.isDone());
            System.out.println("Result : " + result);
        } catch (InterruptedException i) {
            System.out.println("Task interrupted : " + i.getMessage());
        } catch (ExecutionException e) {
            System.out.println("Task execution exception : " + e.getMessage());
        } catch (TimeoutException t) {
            System.out.println("Task timed out : " + t.getMessage());
        }
        
        // Unless we stop it Executor will continue to run
        stopExecutor(exec);
    }
    
    private void invokeCallablesFutures() {
        
        ExecutorService exec = Executors.newWorkStealingPool();
        
        List<Callable<String>> callables = Arrays.asList(
                () -> "Task 1",
                () -> "Task 2",
                () -> "Task 3"
            );

        try {            
            exec.invokeAll(callables)
                .stream()
                .map(future -> {
                    try {
                        return future.get();
                    } catch (InterruptedException e) {
                        throw new IllegalStateException(e);
                    } catch (ExecutionException e) {
                        throw new IllegalStateException(e);
                    }
                })
                .forEach(s -> System.out.println(s));
        } catch (InterruptedException i) {
            System.out.println("Invoke all interrupted : " + i.getMessage());
        }
        
        List<Callable<String>> callables2 = Arrays.asList(
                createCallableWithSleep("Task 1", 3),
                createCallableWithSleep("Task 2", 1),
                createCallableWithSleep("Task 3", 2)
            );

        try {            
            String result = exec.invokeAny(callables2);
            System.out.println("Invoke Any result : " + result);
        } catch (InterruptedException i) {
            System.out.println("Invoke any interrupted : " + i.getMessage());
        } catch (ExecutionException e) {
            System.out.println("Invoke any execution exception : " + e.getMessage());
        }
        
        // Unless we stop it Executor will continue to run
        stopExecutor(exec);
    }

    private void scheduledExecutor() {
        
        ScheduledExecutorService exec = Executors.newScheduledThreadPool(1);
        Runnable task = () -> System.out.println("Scheduling: " + System.nanoTime());
        
        // Delay task start for 3 seconds
        ScheduledFuture<?> future = exec.schedule(task, 3, TimeUnit.SECONDS);

        // Sleep for less than task delay start
        try {            
            TimeUnit.MILLISECONDS.sleep(1337);
        } catch (InterruptedException i) {
            System.out.println("Sleep interrupted : " + i.getMessage());
        }
        
        // Get the delay remaing on the scheduled task
        long remainingDelay = future.getDelay(TimeUnit.MILLISECONDS);

        System.out.println("Remaining delay (ms): " + remainingDelay);
        
        // Schedule task to run every second without an initial delay
        exec.scheduleAtFixedRate(task, 0, 1, TimeUnit.SECONDS);
        
        try {            
            TimeUnit.SECONDS.sleep(10);
        } catch (InterruptedException i) {
            System.out.println("Sleep interrupted : " + i.getMessage());
        }
        
        // Unless we stop it Executor will continue to run
        stopExecutor(exec);
    }
    
    public void synchronization() {
        
        SimpleCounter sc = new SimpleCounter();
        
        ExecutorService exec = Executors.newFixedThreadPool(3);
        
        IntStream.rangeClosed(1, 10000).forEach(i -> exec.submit(sc::unsynchedIncrement));
            
        SimpleCounter sc2 = new SimpleCounter();
        
        IntStream.rangeClosed(1, 10000).forEach(i -> exec.submit(sc2::synchedIncrement));

        SimpleCounter sc3 = new SimpleCounter();
        
        IntStream.rangeClosed(1, 10000).forEach(i -> exec.submit(sc3::synchBlockIncrement));

        // Unless we stop it Executor will continue to run
        stopExecutor(exec);
        
        // Print after close down to make sure all threads have finished
        System.out.println("Unsynchronised counter : " + sc.getCount());
        System.out.println("Unsynchronised counter 2: " + sc.getCount2());
        System.out.println("Synchronised method counter : " + sc2.getCount());
        System.out.println("Synchronised method counter 2 : " + sc2.getCount2());
        System.out.println("Unsynchronised block counter : " + sc3.getCount());
        System.out.println("Synchronised block counter 2 : " + sc3.getCount2());
    }
    
    public void reentrantLocking() {
        
        ExecutorService exec = Executors.newFixedThreadPool(2);
        
        ReentrantLock lock = new ReentrantLock();
        
        exec.submit(() -> {
                lock.lock();
                
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException i) {
                    System.out.println("Task interrupted : " + i.getMessage());
                } finally {
                    System.out.println("Pre-unlock hold count 1: " + lock.getHoldCount());
                    lock.unlock();
                    System.out.println("Post-unlock hold count 1: " + lock.getHoldCount());
                }
            });
        
        exec.submit(() -> {
                System.out.println(lock.toString());
                System.out.println("Hold count: " + lock.getHoldCount());
                System.out.println("Locked: " + lock.isLocked());
                System.out.println("Held by me: " + lock.isHeldByCurrentThread());
                
                System.out.println("Try and obtain lock, but don't block...");
                System.out.println("Lock acquired: " + lock.tryLock());
                
                System.out.println("Block waiting to obtain lock...");
                lock.lock();
                
                try {
                    System.out.println(lock.toString());
                    System.out.println("Hold count 2: " + lock.getHoldCount());
                    System.out.println("Held by me 2: " + lock.isHeldByCurrentThread());
                    System.out.println("Locked 2: " + lock.isLocked());
                } finally {
                    System.out.println("Pre-unlock hold count 2: " + lock.getHoldCount());
                    lock.unlock();
                    System.out.println("Post-unlock hold count 2: " + lock.getHoldCount());
                }
                    
                System.out.println(lock.toString());
                System.out.println("Hold count 3: " + lock.getHoldCount());
                System.out.println("Locked 3: " + lock.isLocked());
            });
            
        // Unless we stop it Executor will continue to run
        stopExecutor(exec);
    }
    
    public void readWriteLocking() {
        ExecutorService exec = Executors.newFixedThreadPool(2);

        Map<String, String> map = new HashMap<>();
        ReadWriteLock lock = new ReentrantReadWriteLock();
        
        exec.submit(() -> {
                lock.writeLock().lock();
                
                try {
                    TimeUnit.SECONDS.sleep(2);
                    map.put("foo", "bar");
                } catch (InterruptedException i) {
                    System.out.println("Sleep interrupted : " + i.getMessage());   
                } finally {
                    lock.writeLock().unlock();
                }
            });

        Runnable readTask = () -> {
                lock.readLock().lock();
                System.out.println("Value of foo is " + map.get("foo"));
                lock.readLock().unlock();
            };
            
        exec.submit(readTask);
        exec.submit(readTask);
        
        // Unless we stop it Executor will continue to run
        stopExecutor(exec);
    }
    
    public void stampedLocking() {
        ExecutorService exec = Executors.newFixedThreadPool(2);

        Map<String, String> map = new HashMap<>();
        StampedLock lock = new StampedLock();
        
        exec.submit(() -> {
                long stamp = lock.writeLock();
                
                try {
                    TimeUnit.SECONDS.sleep(2);
                    map.put("foo", "bar");
                } catch (InterruptedException i) {
                    System.out.println("Sleep interrupted : " + i.getMessage());   
                } finally {
                    lock.unlock(stamp);
                }
            });

        Runnable readTask = () -> {
                long stamp = lock.readLock();
                System.out.println("Value of foo is " + map.get("foo"));
                lock.unlock(stamp);
            };
            
        exec.submit(readTask);
        exec.submit(readTask);

        // Optimistic locking
        StampedLock lock2 = new StampedLock();
        
        exec.submit(() -> {
                try {
                    TimeUnit.MILLISECONDS.sleep(500);
                } catch (InterruptedException i) {
                    System.out.println("Sleep interrupted : " + i.getMessage());   
                }
                
                long stamp = lock2.writeLock();
                System.out.println("Write lock acquired");   
                
                try {
                    TimeUnit.SECONDS.sleep(2);
                } catch (InterruptedException i) {
                    System.out.println("Sleep interrupted : " + i.getMessage());   
                } finally {
                    lock2.unlock(stamp);
                    System.out.println("Write lock released");   
                }
            });
        
        exec.submit(() -> {
                long stamp = lock2.tryOptimisticRead();
                
                try {
                    System.out.println("Optimistic lock valid 1 ? : " + lock2.validate(stamp));   
                    TimeUnit.SECONDS.sleep(1);
                    System.out.println("Optimistic lock valid 2 ? : " + lock2.validate(stamp));   
                    TimeUnit.SECONDS.sleep(2);
                    System.out.println("Optimistic lock valid 3 ? : " + lock2.validate(stamp));   
                } catch (InterruptedException i) {
                    System.out.println("Sleep interrupted : " + i.getMessage());   
                } finally {
                    lock2.unlock(stamp);
                    System.out.println("Optimistic read lock released");   
                }
            });
        
        // Unless we stop it Executor will continue to run
        stopExecutor(exec);
    }
    
    public void multiThreadedPrint() {
            
        ExecutorService exec = Executors.newFixedThreadPool(3);
        Lock printZeroLock = new ReentrantLock();
        AtomicBoolean printZeroFlag = new AtomicBoolean(true);
        AtomicInteger count = new AtomicInteger(1);
        AtomicBoolean exit = new AtomicBoolean(false);
        
        List<Callable<Void>> tasks = Arrays.asList(
          new PrinterThread(PrintType.ZERO, printZeroLock, 
                            printZeroFlag, count, exit),
          new PrinterThread(PrintType.ODD, printZeroLock, 
                            printZeroFlag, count, exit),
          new PrinterThread(PrintType.EVEN, printZeroLock, 
                            printZeroFlag, count, exit)
        );
        
        try {
          exec.invokeAll(tasks);
        } catch (InterruptedException i) {
          System.out.println("Interrupted : " + i.getMessage());
        }
        
        stopExecutor(exec);
    }
      
    class PrinterThread implements Callable<Void> {
        
        private PrintType pt;
        private Lock zLock;
        private AtomicInteger count;
        private AtomicBoolean printZero;
        private AtomicBoolean exit;
        
        public PrinterThread(PrintType type, Lock zeroLock, 
                             AtomicBoolean printZeroFlag,
                             AtomicInteger counter,
                             AtomicBoolean exit) {
              this.pt = type;
              this.zLock = zeroLock;
              this.printZero = printZeroFlag;
              this.count = counter;
              this.exit = exit;
        }
        
        @Override
        public Void call() {
          
          while (!exit.get()) {
            zLock.lock();
            boolean pZero = printZero.get();
            int cnt = count.get();
            
            if (cnt <= 6) {
              switch(pt) {
                case ZERO:
                  if (pZero) {
                    System.out.print("0");
                    printZero.set(false);
                  }
                  break;
                case ODD:
                  if (!pZero && ((cnt % 2) > 0)) {
                    System.out.print(count.getAndIncrement());
                    printZero.set(true);
                  }
                  break;
                case EVEN:
                  if (!pZero && ((cnt % 2) == 0)) {
                    System.out.print(count.getAndIncrement());
                    printZero.set(true);
                  }
                  break;
                default:
                  System.out.println("Unexpected !!");
                  exit.set(true);
              }
            } else {
              exit.set(true);
            }
    
            zLock.unlock();
          } 
          
          return null;
        }
    }
      
    enum PrintType {
        ZERO,
        ODD,
        EVEN;
    }
    
    private Callable<String> createCallableWithSleep(String result, int sleepSecs) {
        return () -> {
            try {
                TimeUnit.SECONDS.sleep(sleepSecs);
                return result;
            } catch (InterruptedException e) {
                throw new IllegalStateException(e);
            }
        };
    }
    
    private static void stopExecutor(ExecutorService exec) {
        try {
            System.out.println("\nAttempting to stop executor...");
            exec.shutdown();
            exec.awaitTermination(5, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            System.out.println("Task interrupted : " + e.getMessage());
        } finally {
            if (!exec.isTerminated()) {
                System.out.println("Blitzing unfinished tasks...");
                exec.shutdownNow();
            }
            
            System.out.println("Executor shutdown completed");
        }
    }

    private void sleep(TimeUnit unit, long value) {
        try {
            unit.sleep(value);
        } catch (InterruptedException i) {
            System.out.println("Sleep interrupted : " + i.getMessage());
        }
    }
    
    class SimpleCounter {
        private int count = 0;
        private int count2 = 0;
        private Object monitor = new Object();
        
        public int getCount() {
            return count;
        }
        
        public int getCount2() {
            return count2;
        }
        
        public void unsynchedIncrement() {
            count++;
            count2 = count2 + 1;
        }
        
        public synchronized void synchedIncrement() {
            count++;
            count2 = count2 + 1;
        }
        
        public void synchBlockIncrement() {
            count++;
            
            synchronized (monitor) {
                count2 = count2 + 1;
            }
        }
        
    }
}