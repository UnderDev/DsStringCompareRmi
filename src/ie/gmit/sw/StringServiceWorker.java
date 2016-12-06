package ie.gmit.sw;

public class StringServiceWorker implements Runnable {

	private String s1;
	private String t1;
	private Algorithm algor;
	private Resultator rs = null;

	public StringServiceWorker(Resultator r, String s, String t, Algorithm algo) {
		this.s1 = s;
		this.t1 = t;
		this.algor = algo;
		this.rs = r;
	}

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
