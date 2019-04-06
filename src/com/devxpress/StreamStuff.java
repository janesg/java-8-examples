package com.devxpress;

import java.time.Instant;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class StreamStuff {
  
    public static void main(String[] args) throws Exception {
        
        // Use infinite stream of integers
        long start1 = Instant.now().toEpochMilli();
        
        Stream.iterate(1, number -> number + 1)
            .map(number -> number * number)
            .limit(50)
            .forEach(number -> System.out.print(number + " "));
            
        long stop1 = Instant.now().toEpochMilli();
        System.out.println("\nTime taken in ms : " + (stop1 - start1));
        
        long start2 = Instant.now().toEpochMilli();
        
        Stream.iterate(1, number -> number + 1)
            .limit(50)
            .map(number -> number * number)
            .forEach(number -> System.out.print(number + " "));
            
        long stop2 = Instant.now().toEpochMilli();
        System.out.println("\nTime taken in ms : " + (stop2 - start2));
        
        long start3 = Instant.now().toEpochMilli();
        
        IntStream.rangeClosed(1, 50)
            .map(number -> number * number)
            .forEach(number -> System.out.print(number + " "));
            
        long stop3 = Instant.now().toEpochMilli();
        System.out.println("\nTime taken in ms : " + (stop3 - start3));
        
        System.out.println("Sum of first 100 integers is " + IntStream.rangeClosed(1, 100).sum());

    }
}