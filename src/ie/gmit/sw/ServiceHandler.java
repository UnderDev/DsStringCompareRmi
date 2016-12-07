package ie.gmit.sw;

import java.io.*;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.util.*;
import java.util.concurrent.*;

import javax.servlet.*;
import javax.servlet.http.*;

public class ServiceHandler extends HttpServlet {

	private static final long serialVersionUID = 1L;

	private String remoteHost = null;
	private volatile static long jobNumber = 0;
	private LinkedList<Requestor> inQueue;
	private Map<String, Resultator> outQueue;
	private ExecutorService executor = Executors.newFixedThreadPool(5);// creating
																		// a
																		// pool
																		// of 5
																		// threads

	public void init() throws ServletException {
		ServletContext ctx = getServletContext();
		remoteHost = ctx.getInitParameter("RMI_SERVER");
		// Reads the value from the <context-param> in web.xml

		inQueue = new LinkedList<Requestor>();
		outQueue = new HashMap<String, Resultator>();
	}

	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.setContentType("text/html");
		PrintWriter out = resp.getWriter();

		// Initialize some request variables with the submitted form info. These
		// are local to this method and thread safe...
		String algorithm = req.getParameter("cmbAlgorithm");
		String s = req.getParameter("txtS");
		String t = req.getParameter("txtT");
		String taskNumber = req.getParameter("frmTaskNumber");

		StringService ss = null;
		try {
			ss = (StringService) Naming.lookup("rmi://localhost:1099/howdayService");
			// Make the remote method invocation. This results in the
			// RemoteMessage object being transferred
			// to us over the network in serialized form.

		} catch (NotBoundException e1) {
			e1.printStackTrace();
		}

		out.print("<html><head><title>Distributed Systems Assignment</title>");
		out.print("</head>");
		out.print("<body>");

		//Resultator result = null;

		// Check to see if its a new job, if so add it to the list
		if (taskNumber == null) {
			taskNumber = new String("T" + jobNumber);
			// Add job to in-queue

			//Create a new Request OBJECT
			Requestor request = new Requestor(s, t, algorithm, taskNumber);
			//Pass the Request Obj to a Worker Class
			Runnable worker = new ServiceHandlerWorker(request,inQueue,outQueue,ss);
			
			//Execute the Worker(fixed pool size) calling execute method of ExecutorService
			executor.execute(worker);
			//Shut down the Executor
			//executor.shutdown();

			// Add the Request to a LinkedList
			inQueue.add(request);

			// Treaded POLL from Queue while its not empty
			//while (!inQueue.isEmpty()) {
				// Take the current job and fire it off to the RMI Method
				// .compare
				//request = inQueue.poll();

				//result = ss.compare(request.getS(), request.getT(), request.getAlgo());
				//outQueue.put(taskNumber, result);
				// System.out.println("Distance: " + result.getResult());
			//}

			jobNumber++;
		} else {
			// Check out-queue for finished job

			// Get the Value associated with job number
			if (outQueue.containsKey(taskNumber)) {
				Resultator outQItem = outQueue.get(taskNumber);

				System.out.println("Checking Status of Task, No:" + taskNumber);

				// Check to see if the Resultator Item is processed
				if (outQItem.isProcessed() == true) {
					// remove the processed item from Map
					outQueue.remove(taskNumber);
					/*
					 * NEXT STEP send completed task back to the client
					 */
					System.out.println("\nTask " + taskNumber + " Successfully Processed and Removed from OutQueue");
					System.out.println(
							"Distance Between String(" + s + ") and String(" + t + ") = " + outQItem.getResult());
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

		// You can use this method to implement the functionality of an RMI
		// client

	}

	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doGet(req, resp);
	}
}