package ie.gmit.sw;

import java.rmi.RemoteException;
import java.util.LinkedList;
import java.util.Map;

/**
* ServiceHandlerWorker Class, Implements Runnable and is in charge of creating a new Threads,
* Class also sends RMI calls to StringService.compare()
* and adds the request to the OutQueue
* 
* @author Scott Coyne
*/
public class ServiceHandlerWorker implements Runnable {
	private LinkedList<Requestor> inQ;
	private Resultator result;
	private StringService stringSer;
	private Map<String, Resultator> outQ;

	public ServiceHandlerWorker(LinkedList<Requestor> inQueue, Map<String, Resultator> outQueue, StringService ss) {
		this.inQ = inQueue;
		this.stringSer = ss;
		this.outQ = outQueue;
	}

	/* Run method fires off a new Thread
	 */
	public void run() {
		Requestor request = inQ.poll();

		try {
			System.out.println("\nAdded Task No:" + request.getTaskNum() + " To Worker Thread");
			//Sleep for 1 sec
			Thread.sleep(1000);
			
			/* Send a RMI to the compare() method with the two string and the algorithm
			 * Return value is a new instance of Resultator,(compare method fires off a new thread
			 * to get the distance of 2 strings with the given algorithm)
			 */
			result = stringSer.compare(request.getS(), request.getT(), request.getAlgo());
			
			//Add the Task num and Resultator to the OutMap
			outQ.put(request.getTaskNum(), result);
			
			//Lazy Exceptions
		} catch (RemoteException e) {		
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
