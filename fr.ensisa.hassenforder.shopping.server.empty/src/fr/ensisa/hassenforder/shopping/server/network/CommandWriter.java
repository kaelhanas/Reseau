package fr.ensisa.hassenforder.shopping.server.network;

import java.io.OutputStream;
import java.util.List;

import fr.ensisa.hassenforder.network.BasicAbstractWriter;
import fr.ensisa.hassenforder.shopping.network.Protocol;
import fr.ensisa.hassenforder.shopping.server.model.Product;

public class CommandWriter extends BasicAbstractWriter {

	public CommandWriter(OutputStream outputStream) {
		super (outputStream);
	}


}
