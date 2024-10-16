package ch14.sec05.exam03;

public class YieldExample {
	public static void main(String[] args) {
		WorkThread workThreadA = new WorkThread("workThreadA");
		WorkThread workThreadB = new WorkThread("workThreadB");	
		workThreadA.start();		
		workThreadB.start();
		
		try { Thread.sleep(3000); } catch (InterruptedException e) {}
		workThreadA.work = false;
		
		try { Thread.sleep(3000); } catch (InterruptedException e) {}
		workThreadA.work = true;
	}
}

