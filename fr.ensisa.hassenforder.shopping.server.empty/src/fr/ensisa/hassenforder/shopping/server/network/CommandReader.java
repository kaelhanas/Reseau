package fr.ensisa.hassenforder.shopping.server.network;


import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import fr.ensisa.hassenforder.network.BasicAbstractReader;
import fr.ensisa.hassenforder.shopping.network.Protocol;

public class CommandReader extends BasicAbstractReader {


	public CommandReader(InputStream inputStream) {
		super (inputStream);
	}

	public void receive() {
		type = readInt ();
		switch (type) {
		case 0 : break;
		}
	}

}
