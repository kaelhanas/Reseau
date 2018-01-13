package fr.ensisa.hassenforder.shopping.client.ui;

import javax.swing.JComponent;

import fr.ensisa.hassenforder.shopping.client.model.Item;

/**
 *
 * @author Hassenforder
 */
public interface GUIListener {
    
    public void onClickClearCaddy ();

    public void onClickIncrementCaddyItemCount (CaddyItem item);

    public void onClickDecrementCaddyItemCount (CaddyItem item);

    public void onClickValidCaddy ();

    public void onClickGetAllCategories();

//    public void onClickGetCategoriesforCategory(ICategory from);

    public void onClickGetProductsForCategory(String path);

    public void onClickBuyProduct (ProductItem item);

    public void onClickInShopDetails (Item item);
    
    public void onGetPicture(JComponent component, int id);

    public void onGetHalfPicture(JComponent component, int id);
}
