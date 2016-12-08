package ie.gmit.sw;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

/**
 * ResultatorImpl Class, extends UnicastRemoteObject and implements all the methods
 * in the interface Resultator.
 * 
 * @author Scott Coyne
 */
public class ResultatorImpl extends UnicastRemoteObject implements Resultator {
	private static final long serialVersionUID = 1L;
	private String s1;
	private String t1;
	private String result;
	private Boolean processed = false;

	public ResultatorImpl() throws RemoteException {
		
	}

	public ResultatorImpl(String s, String t) throws RemoteException {
		this.s1 = s;
		this.t1 = t;
	}

	public String getResult() throws RemoteException {
		
		return result;
	}

	public void setResult(String result) throws RemoteException {
		
		this.result = result;
	}

	public boolean isProcessed() throws RemoteException {		
		return processed;
	}

	public void setProcessed() throws RemoteException {
		this.processed = true;
	}
}