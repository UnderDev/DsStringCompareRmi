package ie.gmit.sw;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
* StringService, Is an Interface that extends remote and contains 
* one method Resultator compare();
*
* @author Scott Coyne
*/
public interface StringService extends Remote {
	public Resultator compare(String s, String t, String algo) throws RemoteException;
}
