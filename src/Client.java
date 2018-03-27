import java.io.*;
import java.net.*;


public class Client implements Runnable {

	private static Socket clientSocket = null;
	private static ObjectOutputStream os = null;
	private static ObjectInputStream is = null;
	private static BufferedReader inputLine = null;
	private static BufferedInputStream bis = null;
	private static boolean closed = false;
	private static boolean name = false;
	private static String username="";

	public static void main(String[] args) {

		int portNumber = 6000;

		String host = "localhost";

		/*
		 * Open a socket on a given host and port. Open input and output streams.
		 */
		try {
			clientSocket = new Socket(host, portNumber);
			inputLine = new BufferedReader(new InputStreamReader(System.in));
			os = new ObjectOutputStream(clientSocket.getOutputStream());
			is = new ObjectInputStream(clientSocket.getInputStream());
		} catch (UnknownHostException e) {
			System.err.println("Unknown " + host);
		} catch (IOException e) {
			System.err.println("No TCPServer found. Please ensure that the TCPServer program is running and try again.");
		}

		/*
		 * If everything has been initialized then we want to write some data to the
		 * socket we have opened a connection to on the port portNumber.
		 */
		if (clientSocket != null && os != null && is != null) {
			try {

				/* Create a thread to read from the server. */
				new Thread(new Client()).start();
				
				while (!closed) {
					if(!name) {
						os.writeObject(username=inputLine.readLine());
						name=true;
						continue;
					}
					String[] in = inputLine.readLine().trim().split(" ");

					/* Check the input for incoming requests */

					if ((in.length == 6)) {
						HTTPRequest request = new HTTPRequest(in[0], in[1], in[2],in[3], in[4], in[5],username);
						os.writeObject(request);
						os.flush();
					}

					else {
						System.out.println("Error in the request parameters");
					}
				}

				/*
				 * Close all the open streams and socket.
				 */
				os.close();
				is.close();
				clientSocket.close();
			} catch (IOException e) {
				System.err.println("IOException:  " + e);
			}


		}
	}

	/*
	 * Create a thread to read from the server.
	 */
	public void run() {

		String responseLine;
		BufferedOutputStream bos = null;

		try {


			while ((responseLine = (String) is.readObject()) != null) {

				System.out.println(responseLine);


				/* Condition for quitting application */

				if (responseLine.indexOf("Bye") != -1)
					break;
			}

			closed = true;
			System.exit(0);

		} catch (IOException | ClassNotFoundException e) {

			System.err.println(e);

		}
	}
}
