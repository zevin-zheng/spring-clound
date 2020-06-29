package com.zhengwei.clound;

public class Test1 {

    public static void main(String[] args) {
        for (int i = 1; i < 1000; i++) {
            boolean flag=true;
            for (int j = 2; j < i/2; j++) {
                if (i%j==0) {
                    flag=false;
                }
            }
            if (flag==true) {
                System.out.println(i+"is prime");
            }
        }
    }
}
