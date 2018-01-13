package fr.ensisa.hassenforder.shopping.client.model;

import java.util.Collection;
import java.util.Map;
import java.util.TreeMap;

/**
 *
 * @author Hassenforder
 */
public class Categories implements ICategory {
    
    private ICategory parent;
    private String name;
    private Map<String, ICategory> children;

    public Categories(String name) {
        this.name = name;
    }

    @Override
    public ICategory getParent() {
        return parent;
    }

    public void setParent(ICategory parent) {
        this.parent = parent;
    }

    @Override
    public String toString() {
        if (name == null || name.isEmpty()) return "Categories";
        return name;
    }

    @Override
    public String getName() {
        return name;
    }
    
    @Override
    public String getFullName() {
        StringBuilder tmp = new StringBuilder();
        if (getParent() != null) {
            tmp.append(getParent().getFullName());
        }
        tmp.append("/");
        tmp.append(getName());
        return tmp.toString();
    }

    public void addChild (ICategory category) {
        getChildren().put (category.getName(), category);
        category.setParent(this);                
    }

    public Map<String, ICategory> getChildren() {
        if (children == null) children = new TreeMap<>();
        return children;
    }    
    
    public void addCategory(ICategory child) {
        if (getChildren().containsKey(child.getName())) {
            //fully disconnect the previous child
            ICategory c = getChildren().get(child.getName());
            c.setParent(null);
            getChildren().remove(child.getName());
        }
        getChildren().put (child.getName(), child);
        child.setParent(this);
    }

    @Override
    public void updateChildren(Collection<String> incomingChildren) {
        if (incomingChildren.isEmpty()) {
            // no child so replace the default Categories node by a Category leaf node 
            // update request will never be issued again
            getParent().getChildren().put(getName(), new Category(getName()));
            setParent (null);
        } else {
            //default is to consider a child can be a collection
            for (String name : incomingChildren) {
                addCategory(new Categories(name));
            }
        }
    }
}
