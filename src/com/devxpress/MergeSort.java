package com.devxpress;

// Big O : n log n
//       : space complexity of n since not an inplace sort (requires temp array)
public class MergeSort<T extends Comparable<T>> {
    
    // Rather than allocating a temporary array internally each time
    // we allow for possibility of reusing same array each time
    public void sort(T[] input, T[] temp) {
        merge(input, temp, 0, input.length - 1);
    }
    
    private void merge(T[] input, T[] temp, int leftStart, int rightEnd) {
        // Base condition signals end
        if (leftStart >= rightEnd) {
            return;
        }
        
        int mid = leftStart + ((rightEnd - leftStart) / 2);
        
        // Merge each half separately
        merge(input, temp, leftStart, mid);       // Left/lower half
        merge(input, temp, mid + 1, rightEnd);    // Right/upper half
        
        // Merge both halves back together
        mergeHalves(input, temp, leftStart, rightEnd);
    }
    
    private void mergeHalves(T[] input, T[] temp, int leftStart, int rightEnd) {
        // define 'internal' ends of each half
        int leftEnd = leftStart + ((rightEnd - leftStart) / 2);
        int rightStart = leftEnd + 1;
        
        // how many elements to copy between arrays ?
        int size = (rightEnd - leftStart) + 1;
        
        // Initialise indices for the 2 sub-arrays
        int leftIdx = leftStart;
        int rightIdx = rightStart;
        
        // Initialise index for the temporary array
        int tempIdx = leftStart;
        
        while ((leftIdx <= leftEnd) && (rightIdx <= rightEnd)) {
            if (input[leftIdx].compareTo(input[rightIdx]) <= 0) {
                temp[tempIdx] = input[leftIdx];
                leftIdx++;
            } else {
                temp[tempIdx] = input[rightIdx];
                rightIdx++;
            }
            
            tempIdx++;
        }
        
        // Now we know that either the left or right side has been 
        // exhausted, but we still need to copy across all remaining 
        // elements from the non-exhausted side
        if (leftIdx > leftEnd) {
            // Left side was exhausted so copy remainder of right side
            // - System.arraycopy(Object src, int srcPos, Object dest, int destPos, int length)
            System.arraycopy(input, rightIdx, temp, tempIdx, (rightEnd - rightIdx) + 1);
        } else {
            // Right side was exhausted so copy remainder of left side
            // - System.arraycopy(Object src, int srcPos, Object dest, int destPos, int length)
            System.arraycopy(input, leftIdx, temp, tempIdx, (leftEnd - leftIdx) + 1);
        }
        
        // Now temp array has been populated, copy the whole thing back to input
        System.arraycopy(temp, leftStart, input, leftStart, size);
    }
}