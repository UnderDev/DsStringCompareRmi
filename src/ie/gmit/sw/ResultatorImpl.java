package ie.gmit.sw;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

//RemoteMessageImpl
public class ResultatorImpl extends UnicastRemoteObject implements Resultator {
	private static final long serialVersionUID = 1L;
	private String s1;
	private String t1;
	private Algorithm algor;
	//private Message message;
	
	public ResultatorImpl() throws RemoteException{
		//this.message = new Message(message);
	}	

	public ResultatorImpl(String s, String t) throws RemoteException {
		this.s1 = s;
		this.t1 = t;
	

	}


	public String getResult() throws RemoteException {
		// TODO Auto-generated method stub
		return "gthgjh";
	}

	public void setResult(String result) throws RemoteException {
		// TODO Auto-generated method stub
		
	}

	public boolean isProcessed() throws RemoteException {
		// TODO Auto-generated method stub
		return false;
	}

	public void setProcessed() throws RemoteException {
		// TODO Auto-generated method stub
		
	}
}