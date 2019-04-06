package com.devxpress;

import static org.junit.Assert.assertEquals;
import org.junit.Test;

public class SearchTest {
    
    @Test
    public void linearStringSearch() {
        
        String[] unsortedStrArr = new String[] {"one", "two", "three", "four", "five", "six", "seven", "eight"};
        String[] unsortedStrArrWithDups = new String[] {"one", "two", "five", "four", "five", "six", "one", "eight"};
        String[] sortedStrArr = new String[] {"eight", "five", "four", "one", "seven", "six", "three", "two"};
        
        Search<String> s = new Search<>();
        assertEquals(-1, s.linearSearch(unsortedStrArr, "eleven"));
        assertEquals(-1, s.linearSearch(unsortedStrArrWithDups, "eleven"));
        assertEquals(-1, s.linearSearch(sortedStrArr, "eleven"));
        assertEquals(4, s.linearSearch(unsortedStrArr, "five"));
        assertEquals(2, s.linearSearch(unsortedStrArrWithDups, "five"));
        assertEquals(1, s.linearSearch(sortedStrArr, "five"));
    }
    
    @Test
    public void linearIntegerSearch() {
        
        Integer[] unsortedIntArr = new Integer[] {5, 3, 6, 7, 13, 9, 1, 43, 15, 29};
        Integer[] unsortedIntArrWithDups = new Integer[] {5, 3, 6, 7, 3, 9, 1, 3, 5, 9};
        Integer[] sortedIntArr = new Integer[] {1, 2, 3, 4, 5, 6, 7, 8, 9};

        Search<Integer> s = new Search<>();
        assertEquals(-1, s.linearSearch(unsortedIntArr, 11));
        assertEquals(-1, s.linearSearch(unsortedIntArrWithDups, 11));
        assertEquals(-1, s.linearSearch(sortedIntArr, 11));
        assertEquals(5, s.linearSearch(unsortedIntArr, 9));
        assertEquals(1, s.linearSearch(unsortedIntArr, 3));
        assertEquals(6, s.linearSearch(sortedIntArr, 7));
    }

    @Test
    public void iterativeBinaryStringSearch() {
        
        String[] sortedStrArr = new String[] {"eight", "five", "four", "one", "seven", "six", "three", "two"};
        
        Search<String> s = new Search<>();
        assertEquals(-1, s.iterativeBinarySearch(sortedStrArr, "eleven"));
        assertEquals(1, s.iterativeBinarySearch(sortedStrArr, "five"));
        assertEquals(0, s.iterativeBinarySearch(sortedStrArr, "eight"));
        assertEquals(7, s.iterativeBinarySearch(sortedStrArr, "two"));
    }
    
    @Test
    public void iterativeBinaryIntegerSearch() {
        
        Integer[] sortedIntArr = new Integer[] {1, 2, 4, 5, 7, 8, 9};

        Search<Integer> s = new Search<>();
        assertEquals(-1, s.iterativeBinarySearch(sortedIntArr, 11));
        assertEquals(-1, s.iterativeBinarySearch(sortedIntArr, 3));
        assertEquals(0, s.iterativeBinarySearch(sortedIntArr, 1));
        assertEquals(6, s.iterativeBinarySearch(sortedIntArr, 9));
    }

    @Test
    public void recursiveBinaryStringSearch() {
        
        String[] sortedStrArr = new String[] {"eight", "five", "four", "one", "seven", "six", "three", "two"};
        
        Search<String> s = new Search<>();
        assertEquals(-1, s.recursiveBinarySearch(sortedStrArr, "eleven"));
        assertEquals(1, s.recursiveBinarySearch(sortedStrArr, "five"));
        assertEquals(0, s.recursiveBinarySearch(sortedStrArr, "eight"));
        assertEquals(7, s.recursiveBinarySearch(sortedStrArr, "two"));
    }
    
    @Test
    public void recursiveBinaryIntegerSearch() {
        
        Integer[] sortedIntArr = new Integer[] {1, 2, 4, 5, 7, 8, 9};

        Search<Integer> s = new Search<>();
        assertEquals(-1, s.recursiveBinarySearch(sortedIntArr, 11));
        assertEquals(-1, s.recursiveBinarySearch(sortedIntArr, 3));
        assertEquals(0, s.recursiveBinarySearch(sortedIntArr, 1));
        assertEquals(6, s.recursiveBinarySearch(sortedIntArr, 9));
    }
    
    @Test
    public void iterativeBinarySearchForFirstLast() {
        
        Integer[] sortedWithDups = new Integer[] {1,1,2,2,3,4,4,4,4,6,7,8,8};
        
        Search<Integer> s = new Search<>();
        assertEquals(0, s.iterativeBinarySearchForFirstLast(sortedWithDups, 1, true));
        assertEquals(1, s.iterativeBinarySearchForFirstLast(sortedWithDups, 1, false));
        assertEquals(11, s.iterativeBinarySearchForFirstLast(sortedWithDups, 8, true));
        assertEquals(12, s.iterativeBinarySearchForFirstLast(sortedWithDups, 8, false));
        assertEquals(5, s.iterativeBinarySearchForFirstLast(sortedWithDups, 4, true));
        assertEquals(8, s.iterativeBinarySearchForFirstLast(sortedWithDups, 4, false));
    }
    
    @Test
    public void findRotationCount() {
        
        Integer[] sortedIntArr = new Integer[] {1, 2, 4, 5, 7, 8, 9};
        Integer[] rotatedIntArr1 = new Integer[] {7, 8, 9, 1, 2, 4, 5};
        Integer[] rotatedIntArr2 = new Integer[] {2, 4, 5, 7, 8, 9, 1};

        Search<Integer> s = new Search<>();
        assertEquals(0, s.findRotationCount(sortedIntArr));
        assertEquals(3, s.findRotationCount(rotatedIntArr1));
        assertEquals(6, s.findRotationCount(rotatedIntArr2));
    }
    
    @Test
    public void circularBinarySearch() {
        
        Integer[] sortedIntArr = new Integer[] {1, 2, 4, 5, 7, 8, 9};
        Integer[] rotatedIntArr1 = new Integer[] {7, 8, 9, 1, 2, 4, 5};
        Integer[] rotatedIntArr2 = new Integer[] {2, 4, 5, 7, 8, 9, 1};

        Search<Integer> s = new Search<>();
        assertEquals(-1, s.circularBinarySearch(sortedIntArr, 12));
        assertEquals(0, s.circularBinarySearch(sortedIntArr, 1));
        assertEquals(6, s.circularBinarySearch(sortedIntArr, 9));
        assertEquals(2, s.circularBinarySearch(sortedIntArr, 4));
        assertEquals(-1, s.circularBinarySearch(rotatedIntArr1, 12));
        assertEquals(3, s.circularBinarySearch(rotatedIntArr1, 1));
        assertEquals(2, s.circularBinarySearch(rotatedIntArr1, 9));
        assertEquals(5, s.circularBinarySearch(rotatedIntArr1, 4));
        assertEquals(-1, s.circularBinarySearch(rotatedIntArr2, 12));
        assertEquals(6, s.circularBinarySearch(rotatedIntArr2, 1));
        assertEquals(5, s.circularBinarySearch(rotatedIntArr2, 9));
        assertEquals(1, s.circularBinarySearch(rotatedIntArr2, 4));
    }
    

}