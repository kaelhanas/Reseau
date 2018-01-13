package fr.ensisa.hassenforder.shopping.server;

import java.util.List;

import fr.ensisa.hassenforder.shopping.server.model.Product;

public interface NetworkListener {

	List<String> getCategory(String path);

	List<Product> getProductsByCategory(String path);

	List<Product> getProductsById(List<Integer> caddy);

	Product getProductById(int item);

	byte[] getPicture(int id);

}
