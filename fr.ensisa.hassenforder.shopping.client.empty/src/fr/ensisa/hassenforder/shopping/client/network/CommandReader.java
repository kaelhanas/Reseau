package fr.ensisa.hassenforder.shopping.client.network;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import fr.ensisa.hassenforder.network.BasicAbstractReader;
import fr.ensisa.hassenforder.network.FileHelper;

import fr.ensisa.hassenforder.shopping.network.Protocol;
import fr.ensisa.hassenforder.shopping.client.model.Product;

public class CommandReader extends BasicAbstractReader {

	private List<String> categories;
	
    public CommandReader(InputStream inputStream) {
        super(inputStream);
    }

    public void receive() {
        type = readInt();
        switch (type) 
        {
        	case 0:
        		break;

        		
        		
        	/*case Protocol.REPLY_OK :
        		break;
        	case Protocol.REPLY_KO :
        		break;
        	case Protocol.REPLY_CATEGORIES :
        		break;
        	case Protocol.REPLY_PRODUCTS :
        		break;
        	case Protocol.REPLY_SHOP_DETAILS :
        		break;
        	case Protocol.REPLY_SHELF_DETAILS :
        		break;
        	case Protocol.REPLY_PICTURE :
        		break;*/
            
            
            
            
        }
    }

	public List<String> getCategories() {
		
		return categories;
	}
    
    
}
