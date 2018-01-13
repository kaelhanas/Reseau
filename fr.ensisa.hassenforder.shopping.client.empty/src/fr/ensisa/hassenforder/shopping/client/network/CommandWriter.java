package fr.ensisa.hassenforder.shopping.client.network;

import java.io.OutputStream;
import java.util.List;

import fr.ensisa.hassenforder.network.BasicAbstractWriter;
import fr.ensisa.hassenforder.shopping.network.Protocol;
import fr.ensisa.hassenforder.shopping.client.model.Item;

public class CommandWriter extends BasicAbstractWriter {

    public CommandWriter(OutputStream outputStream) {
        super(outputStream);
    }

    
    
    public void createRequestCategorieForCatagory()
    {
    	this.writeInt(Protocol.REQUEST_CATEGORIES);
    	System.out.println("Requete suivante créée : "+Protocol.REQUEST_CATEGORIES);
    }
    

}
