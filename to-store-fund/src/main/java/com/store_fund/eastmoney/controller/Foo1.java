package com.store_fund.eastmoney.controller;

/**
 * @Author: tbb
 * @Date: 2022/2/17 23:45
 * @Description:
 */
public class Foo1 {
    private String foo;

    public Foo1() {
    }

    public Foo1(String foo) {
        this.foo = foo;
    }

    public String getFoo() {
        return this.foo;
    }

    public void setFoo(String foo) {
        this.foo = foo;
    }

    @Override
    public String toString() {
        return "Foo1 [foo=" + this.foo + "]";
    }
}
