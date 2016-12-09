package ie.gmit.sw;

import java.rmi.Naming;
import java.rmi.registry.LocateRegistry;

/**
* Runner, Contains Main, and is in-charge of creating the RMI Registry
*
* It also implements the interface StringService
*
* @author Scott Coyne
*/
public class Servant {
	public static void main(String[] args) throws Exception{		
		
		StringService ms = new StringServiceImpl();	
		//Start the RMI registry on port 1099
		LocateRegistry.createRegistry(1099);
		
		//Bind our remote object to the registry with the human-readable name "StringMessageService"
		Naming.rebind("StringMessageService", ms);
		
		//Print a nice message to standard output
		System.out.println("Server Ready to Rock!.");
	}
}
