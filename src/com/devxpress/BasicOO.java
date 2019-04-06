package com.devxpress;

public class BasicOO {
    
    public static void main(String[] args) {
        
        BasicOO boo = new BasicOO();
        boo.doStuff();
    }
    
    public void doStuff() {
        Child c = new Child();
        
        c.doIt();
        System.out.println("c.field = " + c.field);
        System.out.println("((Parent) c).field = " + ((Parent) c).field);
        System.out.println("Parent.field = " + Parent.field);
        
        // Calls inherited method with statically bound value of me from parent
        System.out.println("new Child2().printMe() is: ");
        new Child2().printMe();
        
        // Calls instance method with statically bound value of me from child
        System.out.println("new Child3().printMe() is: ");
        new Child3().printMe();
    }
    
    class Parent {
        static final int field = 1;
    }
    
    class Child extends Parent {
        int field = 2;
        
        void doIt() {
            System.out.println("doIt : super.field = " + super.field);
            System.out.println("doIt : field = " + field);
        }
    }
    
    class Parent2 {
        static final String me = "Parent2";
        
        public void printMe() {
            System.out.println("Parent2 me = " + me);
        }
    }
    
    class Child2 extends Parent2 {
        static final String me = "Child2";
    }
    
    class Child3 extends Parent2 {
        static final String me = "Child3";
        
        @Override
        public void printMe() {
            System.out.println("Child3 me = " + me);
        }
    }
    
}