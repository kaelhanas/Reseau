package fr.ensisa.hassenforder.shopping.server;

import java.net.ServerSocket;
import java.net.Socket;

import fr.ensisa.hassenforder.shopping.network.Protocol;
import fr.ensisa.hassenforder.shopping.server.network.CommandSession;

public class CommandServer extends Thread {

	private ServerSocket server = null;
	private NetworkListener listener = null;

	public CommandServer(NetworkListener listener) {
		super();
		this.listener = listener;
	}

	public void run () {
		try {
			server = new ServerSocket (Protocol.PORT_ID);
			while (true) {
				Socket connection = server.accept();
				CommandSession session = new CommandSession (connection, listener);
				session.start ();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
