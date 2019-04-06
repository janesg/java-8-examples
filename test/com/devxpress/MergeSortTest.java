package com.devxpress;

import static org.junit.Assert.assertEquals;
import org.junit.Test;

import java.util.Arrays;

public class MergeSortTest {
    
    @Test
    public void sortIntegers() {
        
        Integer[] input1 = new Integer[] {8,7,6,5,4,3,2,1};
        Integer[] input2 = new Integer[] {12,-4,25,-4,2,1,54,-1087};
        Integer[] temp = new Integer[input1.length];
        
        MergeSort<Integer> ms = new MergeSort<>();
        
        System.out.println("Array : before : input1 = " + Arrays.toString(input1));
        ms.sort(input1, temp);
        System.out.println("Array : after : input1 = " + Arrays.toString(input1));
        assertEquals(1, input1[0].intValue());
        assertEquals(4, input1[3].intValue());
        assertEquals(8, input1[7].intValue());
        
        System.out.println("Array : before : input2 = " + Arrays.toString(input2));
        ms.sort(input2, temp);
        System.out.println("Array : after : input2 = " + Arrays.toString(input2));
        assertEquals(-1087, input2[0].intValue());
        assertEquals(1, input2[3].intValue());
        assertEquals(54, input2[7].intValue());
    }
    
    @Test
    public void sortStrings() {
        
        String[] input1 = new String[] {"Ethel", "dog", "xray", "Bob", "flob", "", "Xray"};
        String[] temp = new String[input1.length];
        
        MergeSort<String> ms = new MergeSort<>();
        
        System.out.println("Array : before : input1 = " + Arrays.toString(input1));
        ms.sort(input1, temp);
        System.out.println("Array : after : input1 = " + Arrays.toString(input1));
        assertEquals("", input1[0]);
        assertEquals("Xray", input1[3]);
        assertEquals("xray", input1[6]);
    }
}