package fr.ensisa.hassenforder.shopping.client.model;

/**
 *
 * @author hassenforder
 */
public class Product {
    private int id;
    private String category;
    private String name;
    private int quantity;
    private int shopX, shopY;
    private int shelfZX, shelfZY;

    private Product (int id) {
        this.id = id;
        this.category = null;
        this.name = null;
        this.quantity = -1;
        this.shopX = -1;
        this.shopY = -1;
        this.shelfZX = -1;
        this.shelfZY = -1;
    }

    public Product(int id, String category, String name) {
        this (id);
        this.category = category;
        this.name = name;
    }
    
    public Product(int id, int quantity, int shopX, int shopY) {
        this (id);
        this.quantity = quantity;
        this.shopX = shopX;
        this.shopY = shopY;
    }

    public Product(int id, int shelfZX, int shelfZY) {
        this (id);
        this.shelfZX = shelfZX;
        this.shelfZY = shelfZY;
    }

    private Product(int id, String category, String name, int quantity, int shopX, int shopY, int shelfZX, int shelfZY) {
        this.id = id;
        this.category = category;
        this.name = name;
        this.quantity = quantity;
        this.shopX = shopX;
        this.shopY = shopY;
        this.shelfZX = shelfZX;
        this.shelfZY = shelfZY;
    }
    
    public int getId() {
        return id;
    }

    public String getCategory() {
        return category;
    }

    public String getName() {
        return name;
    }

    public int getQuantity() {
        return quantity;
    }

    public int getShopX() {
        return shopX;
    }

    public int getShopY() {
        return shopY;
    }

    public int getShelfZX() {
        return shelfZX;
    }

    public int getShelfZY() {
        return shelfZY;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void setShopX(int shopX) {
        this.shopX = shopX;
    }

    public void setShopY(int shopY) {
        this.shopY = shopY;
    }

    public void setShelfZX(int shelfZX) {
        this.shelfZX = shelfZX;
    }

    public void setShelfZY(int shelfZY) {
        this.shelfZY = shelfZY;
    }
    
    
}
