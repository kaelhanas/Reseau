package fr.ensisa.hassenforder.shopping.server.model;

import java.io.InputStream;
import java.io.PrintStream;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.TreeMap;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import fr.ensisa.hassenforder.network.FileHelper;

/**
 *
 * @author hassenforder
 */
public class Model {

	private List<Product> products;
	private Categories categories;
	private Map<Integer, byte[]> pictures;

	private void save(List<Product> products) {
		try {
			JAXBContext context = JAXBContext.newInstance(Products.class);
			Marshaller m = context.createMarshaller();
			m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
			Products ps = new Products();
			ps.setProducts(products);
			m.marshal(ps, System.out);
		} catch (JAXBException ex) {
			ex.printStackTrace();
		}
	}

	private List<Product> loadProducts() {
		try {
			JAXBContext jc = JAXBContext.newInstance("fr.ensisa.hassenforder.shopping.server.model");
			Unmarshaller unmarshaller = jc.createUnmarshaller();
			InputStream in = getClass().getResourceAsStream("products.xml"); 
			Products products = (Products) unmarshaller.unmarshal(in);
			return products.getProducts();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public List<Product> getProducts() {
		if (products == null) products = loadProducts();
		return products;
	}

	private Map<Integer, byte[]> createPictures() {
		Map<Integer, byte[]> pictures = new TreeMap<>();
		pictures.put(0, FileHelper.readContent("./images/empty.png"));
		return pictures;
	}

	public Map<Integer, byte[]> getPictures() {
		if (pictures == null) pictures = createPictures();
		return pictures;
	}

	private void save(Categories categories) {
		try {
			JAXBContext context = JAXBContext.newInstance(Categories.class);
			Marshaller m = context.createMarshaller();
			m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
			m.marshal(categories, System.out);
		} catch (JAXBException ex) {
			ex.printStackTrace();
		}
	}

	private void dump (Categories categories, PrintStream out) {
		out.println(categories.getFullName());
		for (Categories c : categories.getChildren().values()) {
			dump (c, out);
		}
	}

	private Categories createFromFlatList (CategoryList list) {
		Categories root = new Categories("");
		for (String fullname : list.getCategories()) {
			int p = fullname.lastIndexOf("/");
			if (p == -1) continue;
			String path = fullname.substring(0, p);
			String name = fullname.substring(p+1);
			Categories parent = findCategory(root, path);
			Categories c = new Categories(name);
			parent.addCategory(c);
		}
		return root;
	}
	
	private Categories loadCategories() {
		try {
			JAXBContext jc = JAXBContext.newInstance("fr.ensisa.hassenforder.shopping.server.model");
			Unmarshaller unmarshaller = jc.createUnmarshaller();
			InputStream in = getClass().getResourceAsStream("categories.xml"); 
			CategoryList list = (CategoryList) unmarshaller.unmarshal(in);
			Categories categories = createFromFlatList (list);
//			dump (categories, System.out);
			return categories;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public Categories getCategories() {
		if (categories == null) categories = loadCategories();
		return categories;
	}

    private Categories findCategory (Categories root, String path) {
        if (path == null) return null;
        if (path.isEmpty()) return null;
        Categories crt = root;
        StringTokenizer tokenizer = new StringTokenizer(path, "/");
        while (tokenizer.hasMoreElements()) {
            String step = tokenizer.nextToken("/");
            if (! crt.getChildren().containsKey(step)) return null;
            crt = crt.getChildren().get(step);
        }
        return crt;
    }

}
