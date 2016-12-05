package ie.gmit.sw;

import java.rmi.RemoteException;

//RemoteMessage
public interface Resultator {

	public Resultator compare(String s, String t, Algorithm algo) throws RemoteException;
	public String getResult() throws RemoteException;
	public void setResult(String result) throws RemoteException;
	public boolean isProcessed() throws RemoteException;
	public void setProcessed() throws RemoteException;
}
