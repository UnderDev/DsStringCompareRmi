package ie.gmit.sw;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashMap;

/**
* StringServiceImpl Class, extends UnicastRemoteObject and implements StringService,
* Creates a Map containing a new instance of the Algorithms,
* and also sends threaded requests to StringServiceWorker
* 
* @author Scott Coyne
*/
public class StringServiceImpl extends UnicastRemoteObject implements StringService {
	private static final long serialVersionUID = 1L;
	private static volatile HashMap<String, Algorithm> algoMap = new HashMap<String, Algorithm>();

	public StringServiceImpl() throws RemoteException {
		super();
	}

	public Resultator compare(String s, String t, String algo) throws RemoteException {
		fillMap();
		// Create a new instance of ResultatorImpl()
		Resultator r = new ResultatorImpl();

		/* Create a new thread with a new instance of StringServiceWorker that
		 * passes in a reference to the ResultatorImpl, the two strings and the
		 * new instance of the algorithm chosen. Thread sleeps for 1 sec to simulate real
		 * asynchronous service.
		 */
		Thread thread = new Thread(new StringServiceWorker(r, s, t, algoMap.get(algo)));
		thread.start();

		// Return the instance of Resultator back to the client(Updates when
		// worker thread finishes)
		return r;
	}
	
	/* Fill the Map with the algorithm String name and a new instance of the algorithm itself
	 */
	private void fillMap(){
		/*Working*/
		algoMap.put("Damerau-Levenshtein Distance", new DamerauLevenshtein());
		algoMap.put("Levenshtein", new Levenshtein());
		algoMap.put("Hamming Distance", new HammingDistance());
		algoMap.put("Needleman-Wunsch", new NeedlemanWunsch());
		algoMap.put("JaroâWinkler Distance", new JaroWinklerAlgorithm());
		
		//Implemented but not correct
		algoMap.put("Smith Waterman", new SmithWatermanAlgorithm());
			
		/*Not implemented DEFAULT Levenshtein Algorithm*/
		//Working, but not returning Correct answer
		algoMap.put("Euclidean Distance", new Levenshtein());
		
		//Code not returning distance 	
		algoMap.put("Hirschberg's Algorithm", new Levenshtein());
	}

}
