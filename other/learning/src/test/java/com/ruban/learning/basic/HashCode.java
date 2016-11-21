package com.ruban.learning.basic;

import org.junit.Test;

public class HashCode {

    @Test
    public void testHashcode(){
        StringBuilder sb = new StringBuilder();
        StringBuilder sb2 = new StringBuilder();
        for(int i = 1; i<=10000;i++){
            sb.append("ab");
            sb2.append("ba");
        }
        
        System.out.println(sb.toString().hashCode());
        System.out.println(sb2.toString().hashCode());
    }
}
