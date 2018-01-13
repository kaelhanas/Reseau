package fr.ensisa.hassenforder.shopping.server.model;

import javax.xml.bind.annotation.XmlAttribute;

/**
 *
 * @author hassenforder
 */
public class Product {

    private int id;
    private String category;
    private String name;
    private int quantity;
    private int shopX;
    private int shopY;
    private int shelfZX;
    private int shelfZY;

    public Product() {
		super();
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public void setName(String name) {
		this.name = name;
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

	public Product(int id, String category, String name, int quantity, int shopX, int shopY, int shelfZX, int shelfZY) {
        this.id = id;
        this.category = category;
        this.name = name;
        this.quantity = quantity;
        this.shopX = shopX;
        this.shopY = shopY;
        this.shelfZX = shelfZX;
        this.shelfZY = shelfZY;
    }
    
	@XmlAttribute
    public int getId() {
        return id;
    }

	@XmlAttribute
    public String getCategory() {
        return category;
    }

	@XmlAttribute
    public String getName() {
        return name;
    }

	@XmlAttribute
    public int getQuantity() {
        return quantity;
    }

	@XmlAttribute
    public int getShopX() {
        return shopX;
    }

	@XmlAttribute
    public int getShopY() {
        return shopY;
    }

	@XmlAttribute
    public int getShelfZX() {
        return shelfZX;
    }

	@XmlAttribute
    public int getShelfZY() {
        return shelfZY;
    }

}
