package ie.gmit.sw;

import java.rmi.Remote;
import java.rmi.RemoteException;

//Message Service
public interface StringService extends Remote {
	public Resultator compare(String s, String t, String algo) throws RemoteException;
}
