package com.devxpress;

import java.time.Instant;
import java.util.stream.Stream;

@FunctionalInterface
interface TailCall {
    TailCall get();
  
    default boolean terminated() { 
        return false; 
    }
}

class TailCallTerminate implements TailCall {
    public TailCall get() { 
        throw new Error("Don't call"); 
    }
    
    public boolean terminated() { 
        return true;
    }
}

public class TailRecursionExample {
    
    public static TailCall squareAndPrint(long number, long max) {
        
        System.out.println(number * number);
        
        if (number < max) {
            return () -> squareAndPrint(number + 1, max);
        } else {
            return new TailCallTerminate();
        }
    }
  
    public static void main(String[] args) throws Exception {
        
        long max = Long.parseLong(args[0]);
    
        long start = Instant.now().toEpochMilli();
        
        Stream.iterate(squareAndPrint(1, max), TailCall::get)
            .filter(TailCall::terminated)
            .findFirst();

        long stop = Instant.now().toEpochMilli();
        System.out.println("\nTime taken in ms : " + (stop - start));
    }
}