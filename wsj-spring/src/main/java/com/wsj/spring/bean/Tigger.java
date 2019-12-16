package com.wsj.spring.bean;


public class Tigger {


    private B b;

    public Tigger(){
        System.out.println("执行了构造方法");
    }

    public void init(){
        System.out.println("执行了init方法");
    }

    public void destroy(){
        System.out.println("执行了销毁方法");
    }

    public B getB() {
        return b;
    }

    public void setB(B b) {
        this.b = b;
    }
}
