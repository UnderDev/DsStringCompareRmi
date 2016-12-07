package ie.gmit.sw;

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
			rs.setResult(Integer.toString(algor.distance(s1, t1)));
			try {
				Thread.sleep(20000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			rs.setProcessed();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
