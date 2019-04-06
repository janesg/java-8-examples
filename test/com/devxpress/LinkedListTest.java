package com.devxpress;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import org.junit.Test;

import java.util.Arrays;
import java.util.stream.IntStream;

public class LinkedListTest {
    
    @Test
    public void addCount() {
        LinkedList<Integer> llInt = new LinkedList<>();
        assertEquals(0, llInt.size());
        llInt.add(new Integer(4));
        assertEquals(1, llInt.size());
        llInt.add(new Integer(7));
        assertEquals(2, llInt.size());
    }
    
    @Test
    public void addAtNegativeIndex() {
        LinkedList<Integer> llInt = new LinkedList<>();
        
        try {
            llInt.add(-12, new Integer(4));
            fail("An exception should have been thrown");
        } catch (Exception e) {
            assertEquals(IllegalArgumentException.class, e.getClass());
            assertEquals("Index must be a positive value", e.getMessage());
        }
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void addAtInvalidIndex() {
        LinkedList<Integer> llInt = new LinkedList<>();
        llInt.add(12, new Integer(4));
    }
    
    @Test
    public void addList() {
        LinkedList<Integer> llInt = new LinkedList<>();
        llInt.add(Arrays.asList(2,5,9,2,5,17,-4,0));
        assertEquals(8, llInt.size());
    }
    
    @Test
    public void addAtIndex() {
        LinkedList<Integer> llInt = new LinkedList<>();
        llInt.add(Arrays.asList(2,5,9,2,5,17,-4,0));
        assertEquals(8, llInt.size());
        llInt.add(3, 100);
        assertEquals(9, llInt.size());
        System.out.println("\naddAtIndex() : LinkedList contents : " + llInt.toString());
        assertEquals(new Integer(9), llInt.get(2).data());
        assertEquals(new Integer(100), llInt.get(3).data());
        assertEquals(new Integer(2), llInt.get(4).data());
    }
    
    @Test
    public void addAtHead() {
        LinkedList<Integer> llInt = new LinkedList<>();
        llInt.add(Arrays.asList(2,5,9,2,5,17,-4,0));
        assertEquals(8, llInt.size());
        llInt.add(0, 100);
        assertEquals(9, llInt.size());
        System.out.println("\naddAtHead() : LinkedList contents : " + llInt.toString());
        assertEquals(new Integer(100), llInt.get(0).data());
        assertEquals(new Integer(2), llInt.get(1).data());
    }
    
    @Test
    public void addAtTail() {
        LinkedList<Integer> llInt = new LinkedList<>();
        llInt.add(Arrays.asList(2,5,9,2,5,17,-4,0));
        assertEquals(8, llInt.size());
        llInt.add(8, 100);
        assertEquals(9, llInt.size());
        System.out.println("\naddAtTail() : LinkedList contents : " + llInt.toString());
        assertEquals(new Integer(100), llInt.get(8).data());
    }
    
    @Test
    public void removeFromIndex() {
        LinkedList<Integer> llInt = new LinkedList<>();
        llInt.add(Arrays.asList(2,5,9,2,5,17,-4,0));
        assertEquals(8, llInt.size());
        llInt.remove(3);
        assertEquals(7, llInt.size());
        System.out.println("\nremoveFromIndex() : LinkedList contents : " + llInt.toString());
        assertEquals(new Integer(9), llInt.get(2).data());
        assertEquals(new Integer(5), llInt.get(3).data());
        assertEquals(new Integer(17), llInt.get(4).data());
    }
    
    @Test
    public void removeFromHead() {
        LinkedList<Integer> llInt = new LinkedList<>();
        llInt.add(Arrays.asList(2,5,9,2,5,17,-4,0));
        assertEquals(8, llInt.size());
        llInt.remove(0);
        assertEquals(7, llInt.size());
        System.out.println("\nremoveFromHead() : LinkedList contents : " + llInt.toString());
        assertEquals(new Integer(5), llInt.get(0).data());
        assertEquals(new Integer(9), llInt.get(1).data());
    }
    
    @Test
    public void removeFromTail() {
        LinkedList<Integer> llInt = new LinkedList<>();
        llInt.add(Arrays.asList(2,5,9,2,5,17,-4,0));
        assertEquals(8, llInt.size());
        llInt.remove(7);
        assertEquals(7, llInt.size());
        System.out.println("\nremoveFromTail() : LinkedList contents : " + llInt.toString());
        assertEquals(new Integer(-4), llInt.get(6).data());
    }
    
    @Test
    public void get() {
        LinkedList<Integer> llInt = new LinkedList<>();
        llInt.add(Arrays.asList(2,5,9,2,5,17,-4,0));
        assertEquals(new Integer(9), llInt.get(2).data());
        assertEquals(new Integer(-4), llInt.get(6).data());
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void getMiddleEmptyList() {
        LinkedList<Integer> llInt = new LinkedList<>();
        llInt.getMiddle();
    }
    
    @Test
    public void getMiddleOneElem() {
        LinkedList<Integer> llInt = new LinkedList<>();
        llInt.add(12);
        assertEquals(new Integer(12), llInt.getMiddle().data());
    }
    
    @Test
    public void getMiddleTwoElems() {
        LinkedList<Integer> llInt = new LinkedList<>();
        llInt.add(Arrays.asList(2,5));
        assertEquals(new Integer(5), llInt.getMiddle().data());
    }
    
    @Test
    public void getMiddleOddNumElems() {
        LinkedList<Integer> llInt = new LinkedList<>();
        IntStream.range(1,20).forEach(i -> llInt.add(i));
        assertEquals(new Integer(10), llInt.getMiddle().data());
    }
    
    @Test
    public void getMiddleEvenNumElems() {
        LinkedList<Integer> llInt = new LinkedList<>();
        IntStream.rangeClosed(1,20).forEach(i -> llInt.add(i));
        assertEquals(new Integer(11), llInt.getMiddle().data());
    }
    
    @Test
    public void reverseLinkedList() {
        LinkedList<Integer> llInt = new LinkedList<>();
        IntStream.rangeClosed(1,20).forEach(i -> llInt.add(i));
        assertEquals(new Integer(2), llInt.get(1).data());
        System.out.println("\nBEFORE reverseByIteration() : LinkedList contents : " + llInt.toString());
        llInt.reverseByIteration();
        assertEquals(new Integer(19), llInt.get(1).data());
        System.out.println("\nAFTER reverseByIteration() : LinkedList contents : " + llInt.toString());
    }

}