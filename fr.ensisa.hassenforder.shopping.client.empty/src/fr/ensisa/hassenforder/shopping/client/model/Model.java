package fr.ensisa.hassenforder.shopping.client.model;

import java.awt.Image;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 *
 * @author hassenforder
 */
public class Model {
    
    private List<Product> products;
    private List<Item> caddy;
    private ICategory categories;
    private Map<Integer, Image> pictures;
    private Map<Integer, Image> halfPictures;
    
    public List<Product> getProducts() {
        if (products == null) products = new ArrayList<Product>();
        return products;
    }

    public Map<Integer, Image> getPictures() {
        if (pictures == null) pictures = new TreeMap<>();
        return pictures;
    }
    
    public Map<Integer, Image> getHalfPictures() {
        if (halfPictures == null) halfPictures = new TreeMap<>();
        return halfPictures;
    }
    
    public List<Item> getCaddy() {
        if (caddy == null) caddy = new ArrayList<Item>();
        return caddy;
    }
    
    public ICategory getCategories() {
        if (categories == null) categories = new Categories("");
        return categories;
    }

    public void setCategories(ICategory categories) {
        if (categories == null) return;
        this.categories = categories;
    }
    
    private Item findItem (int id) {
        for (Item item : getCaddy()) {
            if (item.getProduct().getId() == id) return item;
        }
        return null;
    }

    public void addDetailsToCaddy(List<Product> products) {
        for (Product p : products) {
            addDetailsToCaddy (p);
        }
    }
    
    public void addDetailsToCaddy(Product p) {
        Item i = findItem(p.getId());
        if (i != null) {
            Product q = i.getProduct();
            if (p.getQuantity() != -1) q.setQuantity(p.getQuantity());
            if (p.getShopX() != -1) q.setShopX(p.getShopX());
            if (p.getShopY() != -1) q.setShopY(p.getShopY());
            if (p.getShelfZX() != -1) q.setShelfZX(p.getShelfZX());
            if (p.getShelfZY() != -1) q.setShelfZY(p.getShelfZY());
        }
    }
    
}
