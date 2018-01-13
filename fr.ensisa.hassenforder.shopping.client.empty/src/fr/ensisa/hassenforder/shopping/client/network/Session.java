package fr.ensisa.hassenforder.shopping.client.network;

import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;
import javax.imageio.ImageIO;
import fr.ensisa.hassenforder.shopping.client.model.Categories;
import fr.ensisa.hassenforder.shopping.client.model.Category;
import fr.ensisa.hassenforder.shopping.client.model.ICategory;
import fr.ensisa.hassenforder.shopping.client.model.Item;
import fr.ensisa.hassenforder.shopping.client.model.Product;

/**
 *
 * @author Hassenforder
 */
public class Session implements ISession {

    private ICategory root;

    private ICategory getRoot() {
        if (root == null) {
            root = new Categories("");

            ICategory fruits = new Categories("fruits");
            fruits.addCategory(new Category("apple"));
            fruits.addCategory(new Category("orange"));
            fruits.addCategory(new Category("pineapple"));
            root.addCategory (fruits);

            ICategory vegetables = new Categories("vegetables");
            ICategory cabbages = new Categories("cabbages");
            cabbages.addCategory(new Category("sauerkraut"));
            cabbages.addCategory(new Category("cauliflower"));
            cabbages.addCategory(new Category("brocolis"));
            cabbages.addCategory(new Category("Brussels sprouts"));
            vegetables.addCategory(cabbages);
            ICategory beans = new Categories("beans");
            beans.addCategory(new Category("green beans"));
            beans.addCategory(new Category("peas"));
            beans.addCategory(new Category("red beans"));        
            vegetables.addCategory(beans);
            root.addCategory (vegetables);

            ICategory cheeses = new Categories("cheeses");
            ICategory cows = new Categories("cow cheeses");
            ICategory lowfat = new Categories("low fat cow cheeses");
            cows.addCategory(lowfat);
            cheeses.addCategory(cows);
            ICategory goats = new Categories("goat cheeses");
            cheeses.addCategory(goats);
            root.addCategory (cheeses);

            ICategory liquids = new Categories("liquids");
            ICategory alcohols = new Categories("alcohols");
            ICategory strongs = new Categories("strongs");
            ICategory whiskies = new Categories("whiskies");
            ICategory vodkas = new Categories("vodkas");
            ICategory rhums = new Categories("rhums");
            strongs.addCategory(whiskies);
            strongs.addCategory(vodkas);
            strongs.addCategory(rhums);
            alcohols.addCategory(strongs);
            ICategory liquors = new Categories("liquors");
            ICategory raspberries = new Categories("raspberry");
            ICategory pears = new Categories("pears");
            liquors.addCategory(raspberries);
            liquors.addCategory(pears);
            liquids.addCategory(strongs);
            liquids.addCategory(liquors);
            ICategory juices = new Categories("juices");
            ICategory oranges2 = new Categories("oranges");
            ICategory apples2 = new Categories("apples");
            ICategory tangerines = new Categories("tangerines");
            juices.addCategory(oranges2);
            juices.addCategory(apples2);
            juices.addCategory(tangerines);
            liquids.addCategory(juices);
            ICategory waters = new Categories("waters");
            liquids.addCategory(waters);        
            root.addCategory (liquids);
        }
        return root;
    }
    
    private ICategory findCategory (String path) {
        if (path == null) return null;
        if (path.isEmpty()) return null;
        ICategory crt = getRoot();
        StringTokenizer tokenizer = new StringTokenizer(path, "/");
        while (tokenizer.hasMoreElements()) {
            String step = tokenizer.nextToken("/");
            if (! crt.getChildren().containsKey(step)) return null;
            crt = crt.getChildren().get(step);
        }
        return crt;
    }

    @Override
    public boolean open() {
        return true;
    }

    @Override
    public boolean close() {
        return true;
    }
    
    @Override
    public List<String> getCategoriesForCategory(String path) {
        ICategory cat = findCategory (path);
        if (cat == null) return null;
        if (cat.getChildren() == null) return new ArrayList<>();
        return new ArrayList<>(cat.getChildren().keySet());
    }

    @Override
    public List<Product> getProductsForCategory(String path) {
        return null;
    }

    @Override
    public List<Product> getShopDetailsForCaddy(List<Item> caddy) {
        return null;
    }

    @Override
    public Product getDisplayDetailsForItem (Item item) {
        return null;
    }

    @Override
    public Image getPicture(int id) {
        Image bi = null;
        try {
            bi = ImageIO.read(new File ("./images", Integer.toString(id)+".png"));
        } catch (IOException ex) {
        }
        if (bi == null) {
            try {
                bi = ImageIO.read(new File ("./images", "empty.png"));
            } catch (IOException ex) {
            }
        }
        return bi;
    }

}
