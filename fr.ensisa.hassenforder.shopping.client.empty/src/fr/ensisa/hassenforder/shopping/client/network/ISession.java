/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.ensisa.hassenforder.shopping.client.network;

import java.awt.Image;
import java.util.List;
import fr.ensisa.hassenforder.shopping.client.model.Item;
import fr.ensisa.hassenforder.shopping.client.model.Product;

/**
 *
 * @author hassenforder
 */
public interface ISession {

    boolean open ();

    boolean close ();

    List<String> getCategoriesForCategory(String path);

    Product getDisplayDetailsForItem(Item item);

    List<Product> getProductsForCategory(String path);

    List<Product> getShopDetailsForCaddy(List<Item> caddy);

    Image getPicture(int id);
    
}
