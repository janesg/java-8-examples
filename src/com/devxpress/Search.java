package com.devxpress;

public class Search<T extends Comparable<T>> {
    
    /**
     * Works same regardless whether input array is sorted or not
     * Will return position (index) of first matching element
     * 
     * Big O : O(n)
     */
    public int linearSearch(T[] input, T element) {
        
        for (int i = 0; i < input.length; i++) {
            if (input[i].compareTo(element) == 0) {
                return i;
            }
        }
        
        return -1;
    }
    
    /**
     * Only works if input is sorted
     * Will return position (index) of first matching element
     * 
     * Big O : O(logn)
     */
    public int iterativeBinarySearch(T[] input, T element) {
        
        int low = 0;
        int high = input.length - 1;
        
        while (low <= high) {
            // If very large number of elements (low + high) could overflow value of int
            // therefore use second form below to avoid this
            // int mid = (low + high) / 2;
            int mid = low + ((high - low)/ 2);
            System.out.println("Search for: " + element + " / low: " + low + " / high: " + high + " / mid: " + mid);
            
            int compRes = input[mid].compareTo(element);
            
            if (compRes == 0) {
                System.out.println("Found: " + element + " @ mid = " + mid);
                return mid;
            } else if (compRes < 0) {
                // Current array element is less than search element
                // Exclude array element and all lower elements from next search
                low = mid + 1;
            } else {
                // Current array element is greate than search element
                // Exclude array element and all higher elements from next search
                high = mid - 1;
            }
        }
        
        System.out.println("Failed to find: " + element);
        return -1;
    }
    
    /**
     * Only works if input is sorted
     * Will return position (index) of first matching element
     * 
     * Big O : O(logn)
     */
    public int recursiveBinarySearch(T[] input, T element) {
        return recursiveBinarySearch(input, 0, input.length - 1, element);
    }
    
    private int recursiveBinarySearch(T[] input, int low, int high, T element) {
        
        if (low > high) {
            return -1;
        }
        
        int mid = low + ((high - low)/ 2);
        
        int compRes = input[mid].compareTo(element);
        
        if (compRes == 0) {
            return mid;
        } else if (compRes < 0) {
            // Current array element is less than search element
            // Exclude array element and all lower elements from next search
            return recursiveBinarySearch(input, mid + 1, high, element);
        } else {
            // Current array element is greate than search element
            // Exclude array element and all higher elements from next search
            return recursiveBinarySearch(input, low, mid - 1, element);
        }
    }
    
    public int iterativeBinarySearchForFirstLast(T[] input, T element, boolean findFirst) {
        
        int result = -1;
        int low = 0;
        int high = input.length - 1;
        
        while (low <= high) {
            int mid = low + ((high - low) / 2);
        
            int compRes = element.compareTo(input[mid]);
            
            if (compRes == 0) {
                // Search object is equal to current array element
                result = mid;
                if (findFirst) {
                    high = mid - 1;
                } else {
                    low = mid + 1;
                }
            } else if (compRes < 0) {
                // Search object is less than current array element
                // -> search lower half of the array next
                high = mid - 1;
            } else {
                // Search object is greater than current array element
                // -> search upper half of the array next
                low = mid + 1;
            }
        }
        
        return result;
    }
    
    
    // Works when:
    //  - rotation is to right
    //  - no duplicates
    //  => at pivot, value is less than both next and previous
    public int findRotationCount(T[] input) {
        
        int low = 0;
        int high = input.length - 1;
        
        while (low <= high) {
            // Case 1: range is sorted low to high
            //  => low index must be the pivot
            if (input[low].compareTo(input[high]) < 0) {
                return low;
            }
            
            int mid = low + ((high - low) / 2);

            // Case 2: mid is the pivot
            // - avoid index out of bounds by using modulus
            if ((input[mid].compareTo(input[((mid + 1) % input.length)]) < 0) &&
                (input[mid].compareTo(input[((mid - 1 + input.length) % input.length)]) < 0)) {
                return mid;
            } else if (input[mid].compareTo(input[high]) < 0) {
                high = mid - 1;
            } else {
                low = mid + 1;
            }
        }
        
        return 0;
    }

    /**
     * Will return position (index) of matching element
     * in circular sorted array
     * - must be no duplicates in input array
     * 
     * Big O : O(logn)
     */
    public int circularBinarySearch(T[] input, T element) {
        
        int low = 0;
        int high = input.length - 1;
        
        while (low <= high) {
            int mid = low + ((high - low)/ 2);

            // case 1: mid is the searched for element
            if (element.compareTo(input[mid]) == 0) {
                return mid;
            } 
            
            // case 2: upper half is sorted 
            if (input[mid].compareTo(input[high]) <= 0) {
                // is search element in sorted half ?
                if ((element.compareTo(input[mid]) > 0) &&
                    (element.compareTo(input[high])) <= 0) {
                    // case 2A: element in sorted upper half
                    low = mid + 1;
                } else {
                    // case 2B: element in lower half
                    high = mid - 1;
                }
            } else {    // case 3: lower half is sorted
                // is search element in sorted half ?
                if ((element.compareTo(input[mid]) < 0) &&
                    (element.compareTo(input[low])) >= 0) {
                    // case 3A: element in sorted lower half
                    high = mid - 1;
                } else {
                    // case 3B: element in upper half
                    low = mid + 1;
                }
            }
        }
        
        return -1;
    }
    
   
}