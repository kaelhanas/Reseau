package fr.ensisa.hassenforder.shopping.client.model;

import java.awt.Image;
import java.util.List;
import javax.swing.JComponent;

/**
 *
 * @author Hassenforder
 */
public interface ModelListener {
   
    public void notifyStatusChanged (String status);
    public void notifyCaddyChanged (List<Item> caddy);
    public void notifyCaddyItemChanged (Item item);
    public void notifyCategoriesChanged(ICategory categories);
    public void notifyProductsChanged (List<Product> caddy);
    public void notifyComponentChanged(JComponent component, Image bi);
    public void notifyCaddyInShopChanged (List<Item> caddy);
    public void notifyCaddyInDisplayChanged (Item item);
}
