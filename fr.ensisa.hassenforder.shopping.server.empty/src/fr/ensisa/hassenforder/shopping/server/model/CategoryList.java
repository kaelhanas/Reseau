package fr.ensisa.hassenforder.shopping.server.model;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class CategoryList {

	private List<String> categories;
    
    public List<String> getCategories () {
    	return categories;
    }

	public void setCategories(List<String> categories) {
		this.categories = categories;
	}

}
