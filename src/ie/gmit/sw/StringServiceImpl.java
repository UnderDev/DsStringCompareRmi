package ie.gmit.sw;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class StringServiceImpl extends UnicastRemoteObject implements StringService {
	private static final long serialVersionUID = 1L;

	protected StringServiceImpl() throws RemoteException {
		super();
	}

	public Resultator compare(String s, String t, String algo) throws RemoteException {

		
		Algorithm algorithim = null;
		switch (algo) {
		case "Damerau-Levenshtein Distance":
			algorithim= new DamerauLevenshtein();
			
			break;

		case "Levenshtein":
			algorithim = new Levenshtein();
			break;
		}
		
		
		Resultator rs = new ResultatorImpl(s,t);
		algorithim.distance(s, t);

		return rs;
	}

}
