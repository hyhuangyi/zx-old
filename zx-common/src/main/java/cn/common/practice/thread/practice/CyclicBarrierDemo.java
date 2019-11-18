package cn.common.practice.thread.practice;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

public class CyclicBarrierDemo {
    private static final int NUM=5;
    private static CyclicBarrier cyclicBarrier=new CyclicBarrier(NUM);

    public static void main(String[] args) {
        for(int i=0;i<NUM;i++){
            new Thread(()->{
                System.out.println("开始");
                try {
                    //所有线程开始才继续向下继续执行(到达这个点)
                    cyclicBarrier.await();
                    System.out.println("开始执行");
                    Thread.sleep(10000);
                    System.out.println("执行完毕");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (BrokenBarrierException e) {
                    e.printStackTrace();
                }
            }).start();
        }
    }
}
