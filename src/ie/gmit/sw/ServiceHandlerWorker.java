package ie.gmit.sw;

import java.rmi.RemoteException;
import java.util.LinkedList;
import java.util.Map;

public class ServiceHandlerWorker implements Runnable {
	private String s1;
	private String t1;
	private String algor;
	private String taskNum;
	private LinkedList<Requestor> inQ;
	private Resultator result;
	private StringService stringSer;
	private Map<String, Resultator> outQ;
	
	public ServiceHandlerWorker(Requestor request, LinkedList<Requestor> inQueue, Map<String, Resultator> outQueue, StringService ss){
		this.s1=request.getS();
		this.t1=request.getT();
		this.algor=request.getAlgo();
		this.taskNum = request.getTaskNum();
		this.inQ = inQueue;
		this.stringSer = ss;
		this.outQ = outQueue;
		
	}

	public void run(){		
		Requestor request = inQ.poll();
		
		try {
			System.out.println("Added Task No:" + taskNum+" To Worker Thread");
			Thread.sleep(20000);
			result = stringSer.compare(s1, t1, algor);
			outQ.put(taskNum, result);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
