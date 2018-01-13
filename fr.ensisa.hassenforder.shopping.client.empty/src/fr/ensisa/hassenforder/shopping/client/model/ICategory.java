package fr.ensisa.hassenforder.shopping.client.model;

import java.util.Collection;
import java.util.Map;

/**
 *
 * @author Hassenforder
 */
public interface ICategory {
    
    public ICategory getParent();

    public void setParent(ICategory parent);

    public String getName();

    public String getFullName();
    
    public Map<String, ICategory> getChildren();

    public void addCategory(ICategory child);

    public void updateChildren(Collection<String> categories);
}
