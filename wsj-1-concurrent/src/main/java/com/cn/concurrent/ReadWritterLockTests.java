package com.cn.concurrent;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class ReadWritterLockTests {
    private static final ReentrantReadWriteLock rwLock = new ReentrantReadWriteLock();
    private static final Lock rl =rwLock.readLock();
    private static final Lock wl = rwLock.writeLock();

    private int i;
    public static void main(String[] args) throws Exception{
        ReadWritterLockTests t = new ReadWritterLockTests();
        for (int i=0;i<10;i++) {
            new Thread(new RLockRunnable(t),"ReadThread"+i).start();
        }
        new Thread(new WLockRunnable(t),"WriteThread").start();
        TimeUnit.SECONDS.sleep(6);
    }
    private  void write() {
        wl.lock();
        try {
            i++;
            System.out.println(Thread.currentThread().getName()+"修改了"+ i);
        }finally {
            wl.unlock();
        }
    }
    private  int read(){
        rl.lock();
        try {
            System.out.println(Thread.currentThread().getName()+"读取到了"+ i);
            return i;
        }finally {
            rl.unlock();
        }
    }
    static  class  RLockRunnable implements  Runnable {
        ReadWritterLockTests tests;

        public RLockRunnable(ReadWritterLockTests tests) {
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
        ReadWritterLockTests tests;

        public WLockRunnable(ReadWritterLockTests tests) {
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
