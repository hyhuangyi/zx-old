package cn.common.practice.thread.practice;

import cn.common.practice.thread.pool.DBPool;

public class WaitDemo extends Thread{

    private DBPool dbPool;

    public WaitDemo(DBPool dbPool){
        this.dbPool=dbPool;
    }

    @Override
    public void run() {
        synchronized (this.dbPool){
            try {
                System.out.println("放弃锁，进入等待。。。。。。");
                this.dbPool.wait();
                System.out.println("又得到了锁，继续运行");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        DBPool dbPool=new DBPool(4);
        WaitDemo demo=new WaitDemo(dbPool);
        demo.start();

        //开启一个线程唤醒demo线程
        new Thread(()->{
            synchronized (dbPool){
                dbPool.notifyAll();
            }
        }).start();
        try {
            Thread.sleep(10);//休眠10ms,不休眠的话调用 demo.interrupt()会异常
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        //处于wait状态的线程调用interrupt 会抛出java.lang.InterruptedException
        demo.interrupt();

    }
}
