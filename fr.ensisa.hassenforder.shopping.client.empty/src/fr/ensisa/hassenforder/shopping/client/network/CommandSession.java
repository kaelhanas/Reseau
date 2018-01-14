package fr.ensisa.hassenforder.shopping.client.network;

import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.net.Socket;
import java.util.List;
import javax.imageio.ImageIO;

import fr.ensisa.hassenforder.shopping.network.Protocol;
import fr.ensisa.hassenforder.shopping.client.model.Item;
import fr.ensisa.hassenforder.shopping.client.model.Product;

public class CommandSession implements ISession {

    private Socket connection;

    public CommandSession() {
    }

    @Override
    synchronized public boolean close() {
        try {
            if (connection != null) {
                connection.close();
            }
            connection = null;
        } catch (IOException e) {
        }
        return true;
    }

    @Override
    synchronized public boolean open() {
        this.close();
        try {
            connection = new Socket("localhost", Protocol.PORT_ID);
            return true;
        } catch (IOException e) {
            return false;
        }
    }

    @Override
    synchronized public List<String> getCategoriesForCategory(String path) {
        try {
        	
            
            //TODO
            //getcategoryPath()
            
            CommandWriter w = new CommandWriter(connection.getOutputStream());
            w.createRequestCategoriesForCategory(path);
            w.send();
            CommandReader r = new CommandReader(connection.getInputStream());
            r.receive();
            
            if (r.getType() == Protocol.REPLY_CATEGORIES)
            {
            	return r.getCategories();
            }
            
        	/*if (null == null) throw new IOException("to implement");*/
            return null;

            
        } catch (IOException e) {
            return null;
        }
    }

    @Override
    synchronized public List<Product> getProductsForCategory(String path) {
        try {
        	
        	CommandWriter w = new CommandWriter(connection.getOutputStream());
        	w.createRequestProductsForCategory(path);
        	w.send();
        	CommandReader r = new CommandReader(connection.getInputStream());
            r.receive();
            
            if (r.getType() == Protocol.REPLY_CATEGORIES)
            {
            	return r.getProducts();
            }
        	
        	//if (null == null) throw new IOException("to implement");
            return null;

            
        } catch (IOException e) {
            return null;
        }
    }

    @Override
    synchronized public List<Product> getShopDetailsForCaddy(List<Item> caddy) {
        try {
        	
        	/*CommandWriter w = new CommandWriter(connection.getOutputStream());
        	w.createRequestShopDetailsForCaddy(caddy);
        	w.send();*/
        	
        	if (null == null) throw new IOException("to implement");
            return null;
            
            //TODO
            
        } catch (IOException e) {
            return null;
        }
    }

    @Override
    synchronized public Product getDisplayDetailsForItem(Item item) {
        try {
        	if (null == null) throw new IOException("to implement");
            return null;
            //TODO
            
            
        } catch (IOException e) {
            return null;
        }
    }

    private Image loadPicture(String name) {
        Image bi = null;
        
        try {
        	
        	CommandWriter w = new CommandWriter(connection.getOutputStream());
        	//w.createRequestPicture()
        	
            bi = ImageIO.read(new File ("./images", name+".png"));
        } catch (IOException ex) {
        }
        if (bi == null) {
            try {
            	
            	//TODO
            	
                bi = ImageIO.read(new File ("./images", "empty.png"));
            } catch (IOException ex) {
            }
        }
        return bi;
    }

    @Override
    synchronized public Image getPicture(int id) {
        try {
        	
        	//TODO
        	
        	if (null == null) throw new IOException("to implement");
            return null;
        } catch (IOException e) {
            return null;
        }
    }

}
