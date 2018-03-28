import java.io.*;
import java.net.*;
import java.util.*;

public class TCPServer {

	// The server socket.
	private static ServerSocket serverSocket = null;

	// The client socket.
	private static Socket clientSocket = null;

	public static ArrayList<clientThread> clients = new ArrayList<clientThread>();

	public static Queue<HTTPRequest> requests = new LinkedList<HTTPRequest>();

	public static void main(String args[]) {

		int portNumber = 6000;

		System.out.println("TCPServer is running using default port number: " + portNumber);

		/*
		 * Open a server socket on the portNumber (default 6000).
		 */
		try {
			serverSocket = new ServerSocket(portNumber);
		} catch (IOException e) {
			System.out.println("TCPServer Socket cannot be created");
		}

		/*
		 * Create a client socket for each connection and pass it to a new client
		 * thread.
		 */

		int clientNum = 1;
		while (true) {
			try {
				clientSocket = serverSocket.accept();
				clientThread currClient = new clientThread(clientSocket, clients, requests);
				clients.add(currClient);
				currClient.start();
				System.out.println("Client <" + clientNum + "> is connected!");
				clientNum++;

			} catch (IOException e) {
				System.out.println("Client could not be connected");
			}
		}

	}
}

class clientThread extends Thread {

	private String clientName = null;
	private ArrayList<String> clientsNames;

	private ObjectInputStream is = null;
	private ObjectOutputStream os = null;
	private Socket clientSocket = null;
	private final ArrayList<clientThread> clients;
	public static Queue<HTTPRequest> requests;

	public clientThread(Socket clientSocket, ArrayList<clientThread> clients, Queue<HTTPRequest> requests) {

		this.clientSocket = clientSocket;
		this.clients = clients;
		this.requests = requests;

	}

	public void run() {

		ArrayList<clientThread> clients = this.clients;
		Queue<HTTPRequest> requests = this.requests;

		try {

			/*
			 * Create input and output streams for this client.
			 */
			is = new ObjectInputStream(clientSocket.getInputStream());
			os = new ObjectOutputStream(clientSocket.getOutputStream());

			String name;
			while (true) {

				boolean userFound = false;
				synchronized (this) {
					this.os.writeObject("Please enter your name: ");
					this.os.flush();
					name = ((String) this.is.readObject()).trim(); // TO delete the spacing and only get the name

					/*
					 * Check if the username is already taken!
					 */
					if (clients != null) {
						for (clientThread c : clients) {
							if (c.clientName != null && c.clientName.equals("@" + name)) {
								userFound = true;
							}
						}
					}

					if (userFound) {
						this.os.writeObject("Username is taken.... Please choose another UNIQUE username");
						continue;
					}

					if ((name.indexOf('@') == -1) || (name.indexOf('!') == -1)) {
						break;
					} else {
						this.os.writeObject("Username should not contain '@' or '!' characters.");
						this.os.flush();
					}

				}

			}

			/* Welcome the new the client. */

			System.out.println("Client Name is " + name);

			this.os.writeObject("*** Welcome " + name + " to the server ***");
			this.os.flush();

			synchronized (this) {

				for (clientThread currClient : clients) {
					if (currClient != null && currClient == this) {
						clientName = "@" + name;
						break;
					}
				}

				/*
				 * inform other users that a new client has joined the pool :D
				 */
				for (clientThread currClient : clients) {
					if (currClient != null && currClient != this) {
						currClient.os.writeObject(name + " has joined");
						currClient.os.flush();

					}

				}
			}

			/* Start the conversation. */

			while (true) {

				this.os.writeObject("Waiting For Requests:");
				this.os.flush();
				// read the incoming request
				HTTPRequest request = (HTTPRequest) is.readObject();
				this.os.writeObject("Request {\n" + request + "\n}\nreceived Successuflly");
				this.os.flush();
				// add to the queue
				requests.add(request);

				//
				// File file = new File("docroot/" + request.URL + "." +
				// request.AcceptedFormat);
				// String status = file.exists() ? "200 OK" : "404 Not Found";
				// FileInputStream fis = new FileInputStream(file);
				// HTTPResponse response = new HTTPResponse(status, request.Version,
				// System.currentTimeMillis(),
				// request.AcceptedFormat, request.Connection, fis);
				// this.os.writeObject(response);

				/*
				 * to be done when handling the requests ,if the connection value is close then
				 * close the connection and remove the client
				 */
				// this.os.writeObject("*** Bye " + name + " ***");
				// this.os.flush();
				// System.out.println(name + " disconnected.");
				// clients.remove(this);
				// this.is.close();
				// this.os.close();
				// clientSocket.close();

				/*
				 * inform other users that a new client has left the pool :D
				 */
				// synchronized (this) {
				//
				// if (!clients.isEmpty()) {
				//
				// for (clientThread curClient : clients) {
				//
				// if (curClient != null && curClient != this && curClient.clientName != null) {
				// curClient.os.writeObject("*** The user " + name + " disconnected ***");
				// curClient.os.flush();
				// }
				// }
				// }
				// }

				//
			}
		} catch (IOException e) {

			System.out.println(e);

		} catch (ClassNotFoundException e) {

			System.out.println(e);
		}
	}

}
