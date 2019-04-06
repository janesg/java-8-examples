package com.devxpress;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertThat;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.collection.IsArrayContainingInAnyOrder.arrayContainingInAnyOrder;
import static org.hamcrest.collection.IsArrayWithSize.arrayWithSize;

import org.junit.Test;

import java.util.Arrays;
import java.util.stream.IntStream;

public class GSTest {
    
    @Test
    public void topIP() {
        String[] lines = new String[] {
            "10.0.0.1 - the first web server log entry",
            "10.0.0.2 - the second web server log entry",
            "10.0.0.1 - the third web server log entry",
            "10.0.0.2 - the fourth web server log entry",
            "10.0.0.1 - the fifth web server log entry"
        };
        
        String[] results = GS.getTopIP(lines);
        assertEquals(1, results.length);
        assertEquals("10.0.0.1", results[0]);
    }
    
    @Test
    public void topIPStream() {
        String[] lines = new String[] {
            "10.0.0.1 - the first web server log entry",
            "10.0.0.2 - the second web server log entry",
            "10.0.0.1 - the third web server log entry",
            "10.0.0.2 - the fourth web server log entry",
            "10.0.0.1 - the fifth web server log entry"
        };
        
        
        String[] results = GS.getTopIPUsingStream(lines);
        assertEquals(1, results.length);
        assertEquals("10.0.0.1", results[0]);
    }
    
    @Test
    public void topIPMultiple() {
        String[] lines = new String[] {
            "10.0.0.7 - a web server log entry",
            "10.0.0.7 - a web server log entry",
            "10.0.0.1 - a web server log entry",
            "10.0.0.8 - a web server log entry",
            "10.0.0.2 - a web server log entry",
            "10.0.0.1 - a web server log entry",
            "10.0.0.9 - a web server log entry",
            "10.0.0.2 - a web server log entry",
            "10.0.0.1 - a web server log entry",
            "10.0.0.8 - a web server log entry",
            "10.0.0.6 - a web server log entry",
            "10.0.0.2 - a web server log entry"
        };
        
        String[] results = GS.getTopIP(lines);

        assertThat(results, is(arrayWithSize(2)));
        assertThat(results, is(arrayContainingInAnyOrder("10.0.0.1","10.0.0.2")));
    }
    
    @Test
    public void topIPMultipleStream() {
        String[] lines = new String[] {
            "10.0.0.7 - a web server log entry",
            "10.0.0.7 - a web server log entry",
            "10.0.0.1 - a web server log entry",
            "10.0.0.8 - a web server log entry",
            "10.0.0.2 - a web server log entry",
            "10.0.0.1 - a web server log entry",
            "10.0.0.9 - a web server log entry",
            "10.0.0.2 - a web server log entry",
            "10.0.0.1 - a web server log entry",
            "10.0.0.8 - a web server log entry",
            "10.0.0.6 - a web server log entry",
            "10.0.0.2 - a web server log entry"
        };
        
        String[] results = GS.getTopIPUsingStream(lines);

        assertThat(results, is(arrayWithSize(2)));
        assertThat(results, is(arrayContainingInAnyOrder("10.0.0.1","10.0.0.2")));
    }
    
    @Test
    public void myHashMapNoCollisions() {
        GS gs = new GS();
        GS.MyHashMap<Integer, Integer> map = gs.new MyHashMap<>();
        
        IntStream.rangeClosed(1, 10)
            .forEach(i -> map.put(i, i*2));
        
        IntStream.rangeClosed(1, 10)
            .forEach(i -> assertEquals(new Integer(i*2), map.get(i)));
    }
    
    @Test
    public void myHashMapWithCollisions() {
        GS gs = new GS();
        GS.MyHashMap<Integer, Integer> map = gs.new MyHashMap<>();
        
        IntStream.rangeClosed(1, 20)
            .forEach(i -> map.put(i, i*2));
            
        IntStream.rangeClosed(1, 20)
            .forEach(i -> assertEquals(new Integer(i*2), map.get(i)));
    }
    
}