package ie.gmit.sw;

import java.rmi.Naming;
import java.rmi.registry.LocateRegistry;

//Runnable
public class Facade {
	public static void main(String[] args) throws Exception{		
		
		StringService ms = new StringServiceImpl();	
		//Start the RMI registry on port 1099
		LocateRegistry.createRegistry(1099);
		
		//Bind our remote object to the registry with the human-readable name "howdayService"
		Naming.rebind("howdayService", ms);
		
		//Print a nice message to standard output
		System.out.println("Server ready.");
	}
}
