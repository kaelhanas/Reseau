package fr.ensisa.hassenforder.shopping.server.model;

import java.util.Map;
import java.util.TreeMap;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Hassenforder
 */
@XmlRootElement
public class Categories {
    
    private Categories parent;
    private String name;
    private Map<String, Categories> children;

    public Categories() {
    	
    }

    public Categories(String name) {
        this.name = name;
    }

    @XmlTransient
    public Categories getParent() {
        return parent;
    }

    public void setParent(Categories parent) {
        this.parent = parent;
    }

    public String toString() {
        if (name == null || name.isEmpty()) return "Categories";
        return name;
    }

    @XmlAttribute
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
		this.name = name;
	}

	public void setChildren(Map<String, Categories> children) {
		this.children = children;
	}

	@XmlTransient
    public String getFullName() {
        StringBuilder tmp = new StringBuilder();
        if (getParent() != null) {
            tmp.append(getParent().getFullName());
        }
        tmp.append("/");
        tmp.append(getName());
        return tmp.toString();
    }

    public void addChild (Categories category) {
        getChildren().put (category.getName(), category);
        category.setParent(this);                
    }

    @XmlElement
    public Map<String, Categories> getChildren() {
        if (children == null) children = new TreeMap<>();
        return children;
    }    
    
    public void addCategory(Categories child) {
        if (getChildren().containsKey(child.getName())) {
            //fully disconnect the previous child
        	Categories c = getChildren().get(child.getName());
            c.setParent(null);
            getChildren().remove(child.getName());
        }
        getChildren().put (child.getName(), child);
        child.setParent(this);
    }

}
