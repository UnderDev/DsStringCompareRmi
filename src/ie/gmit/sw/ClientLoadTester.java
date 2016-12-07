package ie.gmit.sw;

import java.security.SecureRandom;
import java.util.stream.Collectors;

public class ClientLoadTester {

	public ClientLoadTester() {
	}

	public String createRandomString() {
		int codeLength = 5;
		String id = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
		return new SecureRandom().ints(codeLength, 0, id.length()).mapToObj(id::charAt).map(Object::toString)
				.collect(Collectors.joining());
	}

	/*Used to simulate multiple requests at the same time, not working fully 
	 * 
	 * ClientLoadTester load = new ClientLoadTester(); Requestor request=null;
	 * for (int i = 0; i < 20; i++) { request = new
	 * Requestor(load.createRandomString(), load.createRandomString(),
	 * algorithm, taskNumber); inQueue.add(request); }
	 */
}
