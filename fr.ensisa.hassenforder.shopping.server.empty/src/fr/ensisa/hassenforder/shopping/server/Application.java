package fr.ensisa.hassenforder.shopping.server;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import fr.ensisa.hassenforder.network.FileHelper;
import fr.ensisa.hassenforder.shopping.server.model.Categories;
import fr.ensisa.hassenforder.shopping.server.model.Model;
import fr.ensisa.hassenforder.shopping.server.model.Product;

public class Application implements NetworkListener {

	private CommandServer commands = null;
	private Model model = null;
	
	public void start () {
		model = new Model ();
		commands = new CommandServer(this);
		commands.start();
	}
	
    private Categories findCategory (String path) {
        if (path == null) return null;
        if (path.isEmpty()) return null;
        Categories crt = model.getCategories();
        StringTokenizer tokenizer = new StringTokenizer(path, "/");
        while (tokenizer.hasMoreElements()) {
            String step = tokenizer.nextToken("/");
            if (! crt.getChildren().containsKey(step)) return null;
            crt = crt.getChildren().get(step);
        }
        return crt;
    }

    @Override
	public List<String> getCategory(String path) {
    	Categories cat = findCategory (path);
        if (cat == null) return null;
        if (cat.getChildren() == null) return new ArrayList<>();
        return new ArrayList<>(cat.getChildren().keySet());
	}

	@Override
	public List<Product> getProductsByCategory(String path) {
		if (path == null) return null;
		List<Product> products = new ArrayList<Product>();
		for (Product p : model.getProducts()) {
			if (p.getCategory().startsWith(path, 0)) products.add(p);
		}
		return products;
	}

	private boolean containedIn (List<Integer> caddy, int id) {
		for (Integer i : caddy) {
			if (i == id) return true;
		}
		return false;
	}

	@Override
	public List<Product> getProductsById(List<Integer> caddy) {
		List<Product> products = new ArrayList<Product>();
		for (Product p : model.getProducts()) {
			if (containedIn(caddy, p.getId())) products.add(p);
		}
		return products;
	}

	@Override
	public Product getProductById(int item) {
		for (Product p : model.getProducts()) {
			if (p.getId() == item) return p;
		}
		return null;
	}

	@Override
	public byte[] getPicture(int id) {
		if (model.getPictures().containsKey(id)) {
			return model.getPictures().get(id);
		} else {
			byte [] bitmap = FileHelper.readContent("./images/"+id+".png");
			if (bitmap == null) bitmap = model.getPictures().get(0);
			model.getPictures().put(id, bitmap);
			return bitmap;
		}
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Application m = new Application ();
		m.start();
	}

}
