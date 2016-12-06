package ie.gmit.sw;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class StringServiceImpl extends UnicastRemoteObject implements StringService {
	private static final long serialVersionUID = 1L;

	public StringServiceImpl() throws RemoteException {
		super();
	}

	public Resultator compare(String s, String t, String algo) throws RemoteException {
		Algorithm algorithim = null;
		switch (algo) {
		case "Damerau-Levenshtein Distance":
			algorithim = new DamerauLevenshtein();
			break;

		case "Euclidean Distance":
			algorithim = new Levenshtein();
			break;

		case "Levenshtein":
			algorithim = new Levenshtein();
			break;

		case "Hamming Distance":
			algorithim = new HammingDistance();
			break;

		case "Hirschberg's Algorithm":
			algorithim = new Levenshtein();
			break;

		case "JaroâWinkler Distance":
			algorithim = new Levenshtein();
			break;

		case "Levenshtein Distance":
			algorithim = new Levenshtein();
			break;

		case "Needleman-Wunsch":
			algorithim = new Levenshtein();
			break;

		case "Smith Waterman":
			algorithim = new Levenshtein();
			break;

		default:
			algorithim = new Levenshtein();
			break;
		}

		Resultator r = new ResultatorImpl();

		Thread thread = new Thread(new StringServiceWorker(r, s, t, algorithim));
		thread.start();

		return r;
	}

}
