package ie.gmit.sw;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class StringServiceImpl extends UnicastRemoteObject implements StringService {
	private static final long serialVersionUID = 1L;

	public StringServiceImpl() throws RemoteException {
		super();
	}

	public Resultator compare(String s, String t, String algo) throws RemoteException {
		//Create an instance of Algorithm
		Algorithm algorithm = null;
		
		//Switch on the algorithm passed in from RMI, and create a new instance of that algorithm (Fix + Make prettier)
		switch (algo) {
		case "Damerau-Levenshtein Distance":
			algorithm = new DamerauLevenshtein();
			break;

		case "Euclidean Distance":
			algorithm = new Levenshtein();
			break;

		case "Levenshtein":
			algorithm = new Levenshtein();
			break;

		case "Hamming Distance":
			algorithm = new HammingDistance();
			break;

		case "Hirschberg's Algorithm":
			algorithm = new Levenshtein();
			break;

		case "JaroâWinkler Distance":
			algorithm = new Levenshtein();
			break;

		case "Levenshtein Distance":
			algorithm = new Levenshtein();
			break;

		case "Needleman-Wunsch":
			algorithm = new Levenshtein();
			break;

		case "Smith Waterman":
			algorithm = new Levenshtein();
			break;

		default:
			algorithm = new Levenshtein();
			break;
		}

		//Create a new instance of ResultatorImpl()
		Resultator r = new ResultatorImpl();

		/* Create a new thread with a new instance of StringServiceWorker that passes in a reference to the ResultatorImpl, 
		 * the two strings and the algorithm chosen.
		 * Thread sleeps to simulate real asynchronous service.
		 */
		Thread thread = new Thread(new StringServiceWorker(r, s, t, algorithm));
		thread.start();

		/*Return the instance of Resultator back to the client(Updates when worker thread finishes)
		 */
		return r;
	}

}
