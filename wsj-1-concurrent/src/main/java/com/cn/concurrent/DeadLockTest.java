package com.cn.concurrent;

import java.util.concurrent.TimeUnit;

public class DeadLockTest {

    private Object lock1 = new Object();

    private Object lock2 = new Object();

    public static void main(String[] args) {
        DeadLockTest t = new DeadLockTest();
        new Thread(()->{
            try {
                t.read1();
            }catch (Exception e) {
                e.printStackTrace();
            }

        }).start();
        new Thread(()->{
            try {
                t.read2();
            }catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }

    private void read1()  throws  Exception{
        synchronized (lock1) {
            System.out.println(Thread.currentThread().getName()+"获取了lock1对象的锁");
            TimeUnit.SECONDS.sleep(2);
            synchronized (lock2) {
                System.out.println(Thread.currentThread().getName()+"获取了lock2对象的锁");
            }
        }
        System.out.println(Thread.currentThread().getName()+"read1方法执行完成");
    }

    private void read2()  throws  Exception{
        synchronized (lock2) {
            System.out.println(Thread.currentThread().getName()+"获取了lock2对象的锁");
            TimeUnit.SECONDS.sleep(2);
            synchronized (lock1) {
                System.out.println(Thread.currentThread().getName()+"获取了lock1对象的锁");
            }
        }
        System.out.println(Thread.currentThread().getName()+"read2方法执行完成");
    }

}
