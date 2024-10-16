package ch21.sec05;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class VirtualThreadPoolExample {
	public static void main(String[] args) throws Exception {
		// 작업 내용 정의
		Runnable task = new Runnable() {
			@Override
			public void run() {
				try {
					// 비블로킹 작업: 1~1000까지 누적
					long sum = 0;
					for (int i = 1; i <= 1000; i++) {
						sum += i;
					}
					// 블로킹 작업: I/O 작업이 10ms 있다고 가정
					Thread.sleep(10);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		};

		// 작업 건수 설정
		int taskNum = 10000;

		// 플랫폼 스레드 100개를 풀링해서 사용하는 스레드풀 생성
		ExecutorService threadExecutor = Executors.newFixedThreadPool(100);
		// 작업 처리 요청
		work(taskNum, task, threadExecutor);

		// 가상 스레드를 사용하는 스레드풀 생성
		ExecutorService virtualThreadExecutor = Executors.newVirtualThreadPerTaskExecutor();
		// 작업 처리 요청
		work(taskNum, task, virtualThreadExecutor);
	}

	private static void work(int taskNum, Runnable task, ExecutorService executorService) throws Exception {
		// 시작 시간 측정
		long start = System.nanoTime();

		// 작업 처리
		try (executorService) {
			for (int count = 0; count < taskNum; count++) {
				executorService.execute(task);
			}
		}

		// 모든 작업이 종료될때까지 최대 1시간까지 기다림
		executorService.shutdown();
		executorService.awaitTermination(1, TimeUnit.HOURS);

		// 끝 시간 측정
		long end = System.nanoTime();
		// 작업 처리 시간 계산
		long workTime = end - start;
		System.out.println("작업 처리 시간: " + workTime + " ns");
	}
}
