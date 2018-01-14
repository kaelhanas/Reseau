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

    
    
    public void createRequestCategoriesForCategory(String path)
    {
    	this.writeInt(Protocol.REQUEST_CATEGORIES);
    	this.writeString(path);
    	System.out.println("Requete suivante créée : "+Protocol.REQUEST_CATEGORIES);
    }
    
    public void createRequestProductsForCategory(String path)
    {
    	this.writeInt(Protocol.REQUEST_PRODUCTS);
    	this.writeString(path);
    	System.out.println("Requete suivante créée : "+Protocol.REQUEST_PRODUCTS);
    }



	public void createRequestShopDetailsForCaddy(List<Item> caddy) 
	{
		this.writeInt(Protocol.REQUEST_SHOP_DETAILS);
		this.writeInt(caddy.size());
		for(Item i:caddy) 
		{
			this.writeInt(i.getProduct().getId());
		}
		System.out.println("Requete suivante créée : "+Protocol.REQUEST_SHOP_DETAILS);
		
	}



	public void createRequestGetPicture(int id) 
	{
		this.writeInt(Protocol.REQUEST_PICTURE);
		this.writeInt(id);
		System.out.println("Requete suivante créée : "+Protocol.REQUEST_PICTURE);
		
	}

}
