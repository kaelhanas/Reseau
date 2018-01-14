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
	private List<Product> products;
	int length;
	
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
        		break;*/
        	case Protocol.REPLY_CATEGORIES :
        		length = readInt();
        		for(int i=0;i<length;i++)
        		{
        			categories.add(readString());
        		}
        		break;
        	case Protocol.REPLY_PRODUCTS :
        		length = readInt();
        		for(int i=0;i<length;i++)
        		{
        			products.add(new Product(readInt(),readString(),readString()));
        		}
        		break;
        	/*case Protocol.REPLY_SHOP_DETAILS :
        		break;
        	case Protocol.REPLY_SHELF_DETAILS :
        		break;
        	case Protocol.REPLY_PICTURE :
        		break;*/
            
            
            
            
        }
    }

	public List<String> getCategories() 
	{
		
		return categories;
	}
    
	public List<Product> getProducts()
	{
		return products;
	}
    
}
