package com.cn.concurrent;

import lombok.Synchronized;

import java.util.concurrent.TimeUnit;

public class SynOneWriteMonReadTest {

    private  int i;

    private Object lock = new Object();

    public static void main(String[] args) throws Exception{
        SynOneWriteMonReadTest t = new SynOneWriteMonReadTest();
        for (int i=0;i<10;i++) {
            new Thread(new SynOneWriteMonReadTest.RLockRunnable(t),"ReadThread"+i).start();
        }
        new Thread(new SynOneWriteMonReadTest.WLockRunnable(t),"WriteThread").start();
        TimeUnit.SECONDS.sleep(6);
    }

    private int read(){
        synchronized(lock){
            System.out.println(Thread.currentThread().getName()+"读取到了"+ i);
            return  i;
        }
    }

    private int write() {
        synchronized (lock) {
            i++;
            System.out.println(Thread.currentThread().getName()+"修改了"+ i);
            return i;
        }
    }
    static  class  RLockRunnable implements  Runnable {
        SynOneWriteMonReadTest tests;

        public RLockRunnable(SynOneWriteMonReadTest tests) {
            this.tests = tests;
        }

        @Override
        public void run() {
            while (true) {
                int i = tests.read();
                if (i==100){
                    break;
                }
            }

        }
    }
    static  class  WLockRunnable implements  Runnable {
        SynOneWriteMonReadTest tests;

        public WLockRunnable(SynOneWriteMonReadTest tests) {
            this.tests = tests;
        }

        @Override
        public void run() {
            while (true) {
                tests.write();
                if (tests.read()==100){
                    break;
                }
            }
        }
    }
}
