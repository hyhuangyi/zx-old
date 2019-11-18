package cn.common.practice.thread.practice;

import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

/**
 *
 *类说明：演示Future等的使用
 */
public class UseFuture {
	
	/*实现Callable接口，允许有返回值*/
	private static class UseCallable implements Callable<Integer>{

		private int sum;
		@Override
		public Integer call() throws Exception {
			System.out.println("Callable子线程开始计算");
			//休眠时遭到中段则会抛出异常，线程遇到异常会自我中断
			Thread.sleep(2000);

			for(int i=0;i<5000;i++) {
				sum = sum+i;
			}
			System.out.println("Callable子线程计算完成，结果="+sum);
			return sum;
		}

	}
	
	public static void main(String[] args) 
			throws InterruptedException, ExecutionException {
		
		UseCallable useCallable = new UseCallable();
		FutureTask<Integer> futureTask = new FutureTask<Integer>(useCallable);
		new Thread(futureTask).start();
		Random r = new Random();
		Thread.sleep(1);
		if(r.nextBoolean()) {//随机决定是获得结果还是终止任务
			System.out.println("Get UseCallable result = "+futureTask.get());
		}else {
			System.out.println("中断计算");
			futureTask.cancel(true);
		}
		
	}

}
