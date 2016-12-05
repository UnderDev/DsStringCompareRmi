package ie.gmit.sw;

import java.io.*;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.*;

import javax.servlet.*;
import javax.servlet.http.*;

public class ServiceHandler extends HttpServlet {
	private String remoteHost = null;
	private volatile static long jobNumber = 0;
	private LinkedList<Requestor> inQueue;
	private Map outQueue;

	public void init() throws ServletException {
		ServletContext ctx = getServletContext();
		remoteHost = ctx.getInitParameter("RMI_SERVER");
		// Reads the value from the <context-param> in web.xml

		inQueue = new LinkedList<Requestor>();
		outQueue = new HashMap();
	}

	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.setContentType("text/html");
		PrintWriter out = resp.getWriter();

		// Initialise some request varuables with the submitted form info. These
		// are local to this method and thread safe...
		String algorithm = req.getParameter("cmbAlgorithm");
		String s = req.getParameter("txtS");
		String t = req.getParameter("txtT");
		String taskNumber = req.getParameter("frmTaskNumber");

		out.print("<html><head><title>Distributed Systems Assignment</title>");
		out.print("</head>");
		out.print("<body>");

		if (taskNumber == null) {
			taskNumber = new String("T" + jobNumber);
			Requestor request = new Requestor(algorithm, s, t, taskNumber);
			// Add job to in-queue
			
			// Add the Request to a LinkedList
			inQueue.add(request);

			jobNumber++;
		} else {
			// Check out-queue for finished job
			
			// Get the Value associated with job number
			Object outItem = outQueue.get(jobNumber);
			 //Remove the item in the map by jobNumber
			outQueue.remove(jobNumber);
		}
		

		out.print("<H1>Processing request for Job#: " + taskNumber + "</H1>");
		out.print("<div id=\"r\"></div>");

		out.print("<font color=\"#993333\"><b>");
		out.print("RMI Server is located at " + remoteHost);
		out.print("<br>Algorithm: " + algorithm);
		out.print("<br>String <i>s</i> : " + s);
		out.print("<br>String <i>t</i> : " + t);
		out.print(
				"<br>This servlet should only be responsible for handling client request and returning responses. Everything else should be handled by different objects.");
		out.print(
				"Note that any variables declared inside this doGet() method are thread safe. Anything defined at a class level is shared between HTTP requests.");
		out.print("</b></font>");

		out.print("<P> Next Steps:");
		out.print("<OL>");
		out.print(
				"<LI>Generate a big random number to use a a job number, or just increment a static long variable declared at a class level, e.g. jobNumber.");
		out.print("<LI>Create some type of an object from the request variables and jobNumber.");
		out.print("<LI>Add the message request object to a LinkedList or BlockingQueue (the IN-queue)");
		// out.print("<LI>Return the jobNumber to the client web browser with a
		// wait interval using <meta http-equiv=\"refresh\" content=\"10\">. The
		// content=\"10\" will wait for 10s.");
		out.print("<LI>Have some process check the LinkedList or BlockingQueue for message requests.");
		out.print(
				"<LI>Poll a message request from the front of the queue and make an RMI call to the String Comparison Service.");
		out.print(
				"<LI>Get the <i>Resultator</i> (a stub that is returned IMMEDIATELY by the remote method) and add it to a Map (the OUT-queue) using the jobNumber as the key and the <i>Resultator</i> as a value.");
		out.print(
				"<LI>Return the result of the string comparison to the client next time a request for the jobNumber is received and the <i>Resultator</i> returns true for the method <i>isComplete().</i>");
		out.print("</OL>");

		out.print("<form name=\"frmRequestDetails\">");
		out.print("<input name=\"cmbAlgorithm\" type=\"hidden\" value=\"" + algorithm + "\">");
		out.print("<input name=\"txtS\" type=\"hidden\" value=\"" + s + "\">");
		out.print("<input name=\"txtT\" type=\"hidden\" value=\"" + t + "\">");
		out.print("<input name=\"frmTaskNumber\" type=\"hidden\" value=\"" + taskNumber + "\">");
		out.print("</form>");
		out.print("</body>");
		out.print("</html>");

		out.print("<script>");
		out.print("var wait=setTimeout(\"document.frmRequestDetails.submit();\", 10000);");
		out.print("</script>");

		// You can use this method to implement the functionality of an RMI client
		
		
		//--------------------------------------------- MAKE METHOD FROM BELOW RMI CLIENT------------------------------------------	
		//Ask the registry running on 10.2.2.65 and listening in port 1099 for the instannce of
		//the StringService object that is bound to the RMI registry with the name howdayService.
		StringService ss;
		try {
			ss = (StringService) Naming.lookup("rmi://localhost:1099/howdayService");
			//Make the remote method invocation. This results in the RemoteMessage object being transferred
			//to us over the network in serialized form. 
			Resultator result = ss.compare(s, t, algorithm);
			
			System.out.println("RemoteMessage Object ID: " + result);
			
		} catch (NotBoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		//--------------------------------------------- END METHOD RMI CLIENT---------------------------------------------------------
		
		
		
		
		try {
			findJob(inQueue);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
		}//Gets the head of the queue *Thread This*
	}
	
	public Requestor findJob(LinkedList<Requestor> inQ){
		return inQ.poll();	
	}

	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doGet(req, resp);
	}
}