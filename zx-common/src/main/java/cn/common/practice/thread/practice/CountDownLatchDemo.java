package cn.common.practice.thread.practice;

import java.util.concurrent.CountDownLatch;

public class CountDownLatchDemo {

    private static final int NUM=5;
    //初始化
    static CountDownLatch countDownLatch=new CountDownLatch(NUM);

    public static void main(String[] args) throws Exception{
        System.out.println("开始执行");
        long start=System.currentTimeMillis();
        for(int i=0;i<NUM;i++){
            new Thread(()->{
                System.out.println(Thread.currentThread().getName()+":执行业务");
                countDownLatch.countDown();//线程执行完计数器减一
            }).start();

        }
        //主线程等所有线程完成工作才继续执行后面的代码，当计算器减到0阻塞结束
        countDownLatch.await();
        long end=System.currentTimeMillis();
        System.out.println("所有线程执行结束，耗时："+(end-start)+"毫秒");
    }
}
