package fr.ensisa.hassenforder.shopping.client.model;

/**
 *
 * @author hassenforder
 */
public class Item {
    
    public enum Availability {
        Yes,
        Tight,
        Hot,
        Not,
        ;
    }
    
	private static final int THRESHOLD_1 = 3;
	private static final int THRESHOLD_2 = 6;

	private Product product;
    private int count;

    public Item(Product product) {
        this.product = product;
        this.count = 1;
    }

    public Product getProduct() {
        return product;
    }

    public String getName() {
        return product.getName();
    }

    public int getCount() {
        return count;
    }

    public void inc () {
        ++this.count;
    }

    public void dec () {
        --this.count;
    }

    public Availability getAvailable() {
    	if (count > getProduct().getQuantity()) return Availability.Not;
    	if (count > getProduct().getQuantity()-THRESHOLD_1) return Availability.Hot;
    	if (count > getProduct().getQuantity()-THRESHOLD_2) return Availability.Tight;
        return Availability.Yes;
    }

}
