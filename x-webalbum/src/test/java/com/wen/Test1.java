package com.wen;

import org.junit.jupiter.api.Test;

import java.util.concurrent.TimeUnit;

public class Test1 {
    @Test
    void t1(){
        System.out.println(TimeUnit.HOURS.toSeconds(1));
    }

}
