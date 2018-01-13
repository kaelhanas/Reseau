package fr.ensisa.hassenforder.shopping.client.model;

import java.util.Collection;
import java.util.Map;

/**
 *
 * @author Hassenforder
 */
public class Category implements ICategory {

    private ICategory parent;
    private String name;

    public Category(String name) {
        this.name = name;
    }

    public ICategory getParent() {
        return parent;
    }

    public String getName() {
        return name;
    }

    public void setParent(ICategory parent) {
        this.parent = parent;
    }

    @Override
    public String toString() {
        if (name == null || name.isEmpty()) return "Category";
        return name;
    }

    @Override
    public String getFullName() {
        StringBuilder tmp = new StringBuilder();
        if (getParent() != null) {
            tmp.append(getParent().getFullName());
            tmp.append("/");
        }
        tmp.append(getName());
        return tmp.toString();
    }

    @Override
    public Map<String, ICategory> getChildren() {
        return null;
    }
    
    public void addCategory(ICategory child) {
    }

    @Override
    public void updateChildren(Collection<String> categories) {
    }
}
