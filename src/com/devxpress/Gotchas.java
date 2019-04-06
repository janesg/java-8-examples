package com.devxpress;

public class Gotchas {
    
    public static void main(String[] args) {
        
        String one = "Random";
        String two = "Random";
        String three = "RAndom";
        
        if (one == two) {
            System.out.println("one == two");
        } else {
            System.out.println("one != two");
        }
        
        if (one == three) {
            System.out.println("one == three");
        } else {
            System.out.println("one != three");
        }
        
        System.out.println("My apple is " + Fruit.APPLE);
        
        double x = 8.0;
        System.out.println("Cube root of " + x + " = " + Math.pow(x, 1.0 / 3.0));
        
        // Handling cube root of negative number...
        double y = -8.0;
        System.out.println("Cube root of " + y + " = " + -Math.pow(-y, 1.0 / 3.0));
        
    }
    
    interface Fruity {
        String APPLE = "Jazz";
    }
    
    class Fruit implements Fruity {}
    
}