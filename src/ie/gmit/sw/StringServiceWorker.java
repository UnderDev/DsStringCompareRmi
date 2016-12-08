package ie.gmit.sw;

/**
* StringServiceWorker Class, Implements Runnable and is in charge of creating a new Thread
* 
* @author Scott Coyne
*/
public class StringServiceWorker implements Runnable {

	private String s1;
	private String t1;
	private Algorithm algor;
	private Resultator rs = null;

	/* Constructor takes in an instance of Resultator, the two strings and the required algorithm.
	 */
	public StringServiceWorker(Resultator r, String s, String t, Algorithm algo) {
		this.s1 = s;
		this.t1 = t;
		this.algor = algo;
		this.rs = r;
	}

	/* Run method calls the method setResult in Resultator, passes in the algorithm and strings and calculates the Distance.
	 * The Thread then sleeps for the given amount below, to simulate real asynchronous service.
	 * Before the thread finishes it also calls .setProcessed(); from Resultator, which just sets a boolean to true
	 */
	public void run() {
		try {
			//Sets the distance in Resultator.setResult()
			rs.setResult(Integer.toString(algor.distance(s1, t1)));
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			//Used to change the boolean in isProcessed to true
			rs.setProcessed();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
