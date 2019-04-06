package com.devxpress;

import static org.junit.Assert.assertEquals;
import org.junit.Test;

public class HelloWorldTest {
    
    @Test
    public void sayHiToBob() {
        HelloWorld hw = new HelloWorld();
        assertEquals("Hello there Bob", hw.sayHi("Bob"));
    }
}