package com.pews.brightdreamsfoundation;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import sun.misc.Unsafe;

@SpringBootTest
public class GeneralTest {
    @Test
    public void finallyTest() {
        System.out.println(testReturn());
    }

    public int testReturn() {
        try {
            return 1;
        } catch (Exception e) {
            return 2;
        } finally {
            return 3;
        }
    }

    @Test
    public void testUnSafe() {
        Unsafe unsafe = Unsafe.getUnsafe();
        long l = unsafe.allocateMemory(1024L);
    }

    public void testString() {
        String s = "1" + "2";
    }
}
