package ie.gmit.sw;

import java.io.*;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.util.*;
import java.util.concurrent.*;

import javax.servlet.*;
import javax.servlet.http.*;

/**
* ServiceHandler Class, Waits for a doPost() request from a Web Application,
* The doPost() then calls the doGet() method,
* Connects to the local Registry and invokes its methods,
* Adds to an InQueue(Threaded) and deletes from an OutQueue when Tasks are finished,
* 
* ServiceHandler Refreshes the page every 10Seconds, until the Task given is Completed.
* 
* @author Scott Coyne
*/
public class ServiceHandler extends HttpServlet {

	private static final long serialVersionUID = 1L;

	private String remoteHost = null;
	private volatile static long jobNumber = 0;
	private volatile LinkedList<Requestor> inQueue;
	private volatile Map<String, Resultator> outQueue;
	private volatile ExecutorService executor;
	
	private volatile String distance = "";
	private final int THREAD_POOL_SIZE = 3;
	private volatile boolean checkProcessed = false;

	//Init Method to Initialize everything on startup 
	public void init() throws ServletException {
		ServletContext ctx = getServletContext();
		remoteHost = ctx.getInitParameter("RMI_SERVER");
		// Reads the value from the <context-param> in web.xml
		
		inQueue = new LinkedList<Requestor>();
		outQueue = new HashMap<String, Resultator>();
		executor = Executors.newFixedThreadPool(THREAD_POOL_SIZE);
	}

	
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.setContentType("text/html");
		PrintWriter out = resp.getWriter();

		// Initialize some request variables with the submitted form info.
		// These are local to this method and thread safe...
		String algorithm = req.getParameter("cmbAlgorithm");
		String s = req.getParameter("txtS");
		String t = req.getParameter("txtT");
		String taskNumber = req.getParameter("frmTaskNumber");

		StringService ss = null;
		try {
			ss = (StringService) Naming.lookup("rmi://localhost:1099/StringMessageService");
			/*
			 * Make the remote method invocation. This results in the
			 * RemoteMessage object being transferred to us over the network in
			 * serialized form.
			 */
		} catch (NotBoundException e1) {
			e1.printStackTrace();
		}

		out.print("<html><head><title>Distributed Systems Assignment</title>");
		out.print("</head>");
		out.print("<body>");

		// Check to see if its a new Task (New Web App Submission)
		if (taskNumber == null) {
			taskNumber = new String("T" + jobNumber);

			//Reset checkProcessed to false
			checkProcessed = false;

			// Create a new Request OBJECT
			Requestor request = new Requestor(s, t, algorithm, taskNumber);

			// Add Task to in-queue
			inQueue.add(request);

			// Pass the Request Obj to a Worker Class (Thread)
			Runnable worker = new ServiceHandlerWorker(inQueue, outQueue, ss);

			// Execute the Worker(fixed_pool_size)
			executor.execute(worker);

			// Increment jobNumber
			jobNumber++;
		} else {
			// ELSE - Check outQueue for finished job

			// Get the Value associated with the current job number
			if (outQueue.containsKey(taskNumber)) {
				// Get the Resultator item from the MAP by Current taskNumber
				Resultator outQItem = outQueue.get(taskNumber);

				System.out.println("\nChecking Status of Task No:" + taskNumber);

				checkProcessed = outQItem.isProcessed();

				// Check to see if the Resultator Item is Processed
				if (checkProcessed == true) {
					// Remove the processed item from Map by taskNumber
					outQueue.remove(taskNumber);
					//Get the Distance of the Current Task
					distance = outQItem.getResult();

					System.out.println("\nTask " + taskNumber + " Successfully Processed and Removed from OutQueue");
					System.out.println("Distance Between String (" + s + ") and String (" + t + ") = " + distance);
				}
			}
		}

		out.print("<H1>Processing request for Job#: " + taskNumber + "</H1>");
		out.print("<div id=\"r\"></div>");

		out.print("<font color=\"#993333\"><b>");
		out.print("RMI Server is located at " + remoteHost);
		out.print("<br>Algorithm: " + algorithm);
		out.print("<br>String <i>s</i> : " + s);
		out.print("<br>String <i>t</i> : " + t);

		//If the task is complete, Show the Distance, otherwise refresh page and display checking 
		if (checkProcessed == true)
			out.print("<br><br>Distance: " + distance + "<br>");
		else {
			out.print("<form name=\"frmRequestDetails\">");// RefreshPage
			out.print("<br><br>Checking Distance, Please Wait..<br>");
		}

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
		
		// Shut down the Executor (not used) Maby on finalize?
		// executor.shutdown();
	}

	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doGet(req, resp);
	}
}