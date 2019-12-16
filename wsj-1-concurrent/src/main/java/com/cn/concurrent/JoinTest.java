package com.cn.concurrent;

import java.util.concurrent.TimeUnit;

public class JoinTest {
    public static void main(String[] args) {
        Thread main = Thread.currentThread();

        Thread t3 = new Thread(new JoinTestRunnalb(main),"t3");

        Thread t2 = new Thread(new JoinTestRunnalb(t3),"t2");
        Thread t1 = new Thread(new JoinTestRunnalb(t2),"t1");

        t1.start();
        t2.start();
        t3.start();
        System.out.println("main 线程执行结束");
    }
    static class JoinTestRunnalb implements Runnable {

        public JoinTestRunnalb(Thread t) {
            this.t = t;
        }

        Thread t;
        @Override
        public void run() {
            try {
                System.out.println(Thread.currentThread().getName()+"开始执行！");
                t.join();
                TimeUnit.SECONDS.sleep(2);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread().getName()+"执行结束！");
        }
    }
}
