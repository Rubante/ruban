package com.ruban.learning.basic;

import org.junit.Test;

public class IntReverse {

    public static int reverse(int num) {
        int result = 0;

        while (0 < num) {
            int temp = num % 10;
            num = num / 10;
            result = result * 10 + temp;
        }

        return result;
    }

    public static String add(String num1, String num2) {

        int[] intArr1 = new int[num1.length()];
        int[] intArr2 = new int[num2.length()];

        for (int i = 0; i < num1.length(); i++) {
            intArr1[num1.length() - i - 1] = Integer.parseInt((num1.charAt(i)) + "");
        }

        for (int i = 0; i < num2.length(); i++) {
            intArr2[num2.length() - i - 1] = Integer.parseInt(num2.charAt(i) + "");

        }

        int[] result = null;
        if (num1.length() > num2.length()) {
            result = new int[num1.length() + 1];
        } else {
            result = new int[num2.length() + 1];
        }

        for (int i = 0; i < result.length; i++) {
            int temp = 0;
            if (i < num1.length()) {
                temp += intArr1[i];

            }
            if (i < num2.length()) {
                temp += intArr2[i];
            }

            temp += result[i];

            // 进位操作
            if (temp > 10) {
                result[i + 1] += 1;
                result[i] = temp - 10;
            } else {
                result[i] = temp;
            }
        }

        String str = "";
        for (int i = result.length - 1; i >= 0; i--) {
            str += String.valueOf(result[i]);
        }

        if (str.startsWith("0")) {
            str = str.substring(1);
        }

        return str;
    }

    @Test
    public void testSwitch() {

        char a = 'b';

        switch (a) {
        case 'a':
            System.out.println("a");
        case 'b':
            System.out.println("b");
        case 'c':
            System.out.println("c");
        default:
            System.out.println("default");
        }
    }

    @Test
    public void test() {
        System.out.println(987654321 + 10);
        System.out.println(IntReverse.reverse(123456789));

        System.out.println(IntReverse.add("1111111111111111", "22222222222222222222"));
    }

    @Test
    public void integer() {
        System.out.println(Integer.MAX_VALUE);
        System.out.println(Integer.MIN_VALUE);
    }
}
