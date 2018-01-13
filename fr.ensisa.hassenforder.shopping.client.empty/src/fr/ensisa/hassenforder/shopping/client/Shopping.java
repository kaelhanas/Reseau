package fr.ensisa.hassenforder.shopping.client;

import java.awt.Image;
import java.util.List;
import javax.swing.JComponent;
import javax.swing.SwingUtilities;
import fr.ensisa.hassenforder.shopping.client.model.Categories;
import fr.ensisa.hassenforder.shopping.client.model.Category;
import fr.ensisa.hassenforder.shopping.client.model.ICategory;
import fr.ensisa.hassenforder.shopping.client.model.Item;
import fr.ensisa.hassenforder.shopping.client.model.Model;
import fr.ensisa.hassenforder.shopping.client.model.ModelListener;
import fr.ensisa.hassenforder.shopping.client.model.Product;
import fr.ensisa.hassenforder.shopping.client.network.CommandSession;
import fr.ensisa.hassenforder.shopping.client.network.ISession;
import fr.ensisa.hassenforder.shopping.client.network.Session;
import fr.ensisa.hassenforder.shopping.client.ui.CaddyItem;
import fr.ensisa.hassenforder.shopping.client.ui.GUI;
import fr.ensisa.hassenforder.shopping.client.ui.GUIListener;
import fr.ensisa.hassenforder.shopping.client.ui.ProductItem;

/**
 *
 * @author hassenforder
 */
public class Shopping implements GUIListener {

    private Model model;
    private GUI gui;
    private ModelListener listener;
    private ISession session;

    public Shopping() {
        model = new Model ();
        gui = new GUI ();
        gui.setListener(this);
        listener = gui;
        session = new CommandSession();
        session.open();
    }

    public Model getModel() {
        return model;
    }

    public GUI getGui() {
        return gui;
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                Shopping shopping = new Shopping();
                shopping.getGui().setVisible(true);
            }
        });
    }

    @Override
    public void onClickClearCaddy() {
        model.getCaddy().clear();
        listener.notifyCaddyChanged(model.getCaddy());
    }

    @Override
    public void onClickIncrementCaddyItemCount(CaddyItem caddyItem) {
        Item item = caddyItem.getItem();
        item.inc();
        listener.notifyCaddyItemChanged(item);
    }

    @Override
    public void onClickDecrementCaddyItemCount(CaddyItem caddyItem) {
        Item item = caddyItem.getItem();
        item.dec();
        if (item.getCount() == 0) {
            model.getCaddy().remove(item);
            listener.notifyCaddyChanged(model.getCaddy());
        } else {
            listener.notifyCaddyItemChanged(item);
        }
    }
    
    @Override
    public void onClickGetAllCategories() {
        new Thread () {

            // stress the server to load all categories one after the other
            private ICategory getCategory (ICategory from) {
                List<String> names = session.getCategoriesForCategory(from.getFullName());
                if (names == null) return null;
                if (names.isEmpty()) return new Category(from.getName());
                for (String name : names) {
                    ICategory guess = new Categories(name);
                    from.addCategory(guess);
                    ICategory chosed = getCategory(guess);
                    if (chosed == null) return null;
                    from.addCategory(chosed);
                }
                return from;
            }
            
            @Override
            public void run () {
/*
 * HM hard load : one request to the server and the tree comes
 *              ICategory root = session.getAllCategories ();
 */
/*
 * HM stress load : many lazy requests but all together until the tree is full
 *
 */
                ICategory root = getCategory (new Categories(""));
                if (root != null) {
                    model.setCategories(root);
                    SwingUtilities.invokeLater(new Runnable() {
                        @Override
                        public void run() {
                            listener.notifyCategoriesChanged(model.getCategories());
                            listener.notifyStatusChanged ("getAllCategories : OK");
                        }
                    });
                } else {
                    SwingUtilities.invokeLater(new Runnable() {
                        @Override
                        public void run() {
                            listener.notifyStatusChanged ("getAllCategories : FAIL");
                        }
                    });
                }
            }
        }.start();
    }

/*
 * HM lazy load : one lazy request to open one branch of the tree
 *
    @Override
    public void onClickGetCategoriesforCategory(ICategory from) {
        new Thread () {
            @Override
            public void run () {
                List<String> categories = session.getCategoriesForCategory((from != null) ? from.getFullName() : "/");
                if (categories != null) {
                    from.updateChildren (categories);
                    SwingUtilities.invokeLater(new Runnable() {
                        @Override
                        public void run() {
                            listener.notifyCategoriesChanged(model.getCategories());
                            listener.notifyStatusChanged ("getCategoriesforCategory : OK");
                        }
                    });
                } else {
                    SwingUtilities.invokeLater(new Runnable() {
                        @Override
                        public void run() {
                            listener.notifyStatusChanged ("getCategoriesforCategory : FAIL");
                        }
                    });
                }
            }
        }.start();
    }
*/
    @Override
    public void onClickGetProductsForCategory(String path) {
        new Thread () {
            @Override
            public void run () {
                List<Product> products = session.getProductsForCategory (path);
                if (products != null) {
                    model.getProducts().clear();
                    model.getProducts().addAll(products);
                    SwingUtilities.invokeLater(new Runnable() {
                        @Override
                        public void run() {
                            listener.notifyProductsChanged (model.getProducts());
                            listener.notifyStatusChanged ("GetProductForCategory : OK");
                        }
                    });
                } else {
                    SwingUtilities.invokeLater(new Runnable() {
                        @Override
                        public void run() {
//HM
//                            listener.notifyProductsChanged (model.getProducts());
                            listener.notifyStatusChanged ("GetProductForCategory : FAIL");
                        }
                    });
                }
            }
        }.start();
    }

    @Override
    public void onClickBuyProduct(ProductItem item) {
        Product product = item.getProduct();
        model.getCaddy().add (new Item(product));
        listener.notifyCaddyChanged(model.getCaddy());
    }

    @Override
    public void onClickValidCaddy() {
        new Thread () {
            @Override
            public void run () {
                List<Product> products = session.getShopDetailsForCaddy (model.getCaddy());
                if (products != null) {
                    model.addDetailsToCaddy (products);
                    SwingUtilities.invokeLater(new Runnable() {
                        @Override
                        public void run() {
                            listener.notifyCaddyInShopChanged(model.getCaddy());
                            listener.notifyStatusChanged ("validCaddy : OK");
                        }
                    });
                } else {
                    SwingUtilities.invokeLater(new Runnable() {
                        @Override
                        public void run() {
//HM
//                            listener.notifyCaddyInShopChanged(model.getCaddy());
                            listener.notifyStatusChanged ("validCaddy : FAIL");
                        }
                    });
                }
            }
        }.start();
    }

    @Override
    public void onClickInShopDetails(Item item) {
        new Thread () {
            @Override
            public void run () {
                Product product = session.getDisplayDetailsForItem (item);
                if (product != null) {
                    model.addDetailsToCaddy (product);
                    SwingUtilities.invokeLater(new Runnable() {
                        @Override
                        public void run() {
                            listener.notifyCaddyInDisplayChanged(item);
                            listener.notifyStatusChanged ("inShopDetails : OK");
                        }
                    });
                } else {
                    SwingUtilities.invokeLater(new Runnable() {
                        @Override
                        public void run() {
//HM
                            listener.notifyCaddyInDisplayChanged(item);
                            listener.notifyStatusChanged ("inShopDetails : FAIL");
                        }
                    });
                }
            }
        }.start();
    }

    @Override
    public void onGetPicture(JComponent component, int id) {
        Image bi = model.getPictures().get(id);
        if (bi != null) listener.notifyComponentChanged(component, bi);
        else {
            new Thread () {
                @Override
                public void run () {
                    Image bi2 = session.getPicture(id);
                    if (bi2 != null) {
                        model.getPictures().put(id, bi2);
                        SwingUtilities.invokeLater(new Runnable() {
                            @Override
                            public void run() {
                                listener.notifyComponentChanged(component, bi2);
                                listener.notifyStatusChanged ("getPicture : OK");
                            }
                        });
                    } else {
                        SwingUtilities.invokeLater(new Runnable() {
                            @Override
                            public void run() {
                                listener.notifyStatusChanged ("getPicture : FAIL");
                            }
                        });
                    }
                }
            }.start();
        }
    }

    @Override
    public void onGetHalfPicture(JComponent component, int id) {
        Image bi2 = model.getHalfPictures().get(id);
        if (bi2 != null) listener.notifyComponentChanged(component, bi2);
        else {
            Image bi1 = model.getPictures().get(id);
            if (bi1 != null) {
                Image bi_scaled = bi1.getScaledInstance(24, 24, Image.SCALE_FAST);
                model.getHalfPictures().put(id, bi_scaled);
                listener.notifyComponentChanged(component, bi_scaled);
            } else {
                new Thread () {
                    @Override
                    public void run () {
                        Image bi_initial = session.getPicture(id);
                        if (bi_initial != null) {
                            model.getPictures().put(id, bi_initial);
                            Image bi_scaled = bi_initial.getScaledInstance(24, 24, Image.SCALE_FAST);
                            model.getHalfPictures().put(id, bi_scaled);
                            SwingUtilities.invokeLater(new Runnable() {
                                @Override
                                public void run() {
                                    listener.notifyComponentChanged(component, bi_scaled);
                                    listener.notifyStatusChanged ("getPicture : OK");
                                }
                            });
                        } else {
                            SwingUtilities.invokeLater(new Runnable() {
                                @Override
                                public void run() {
                                    listener.notifyStatusChanged ("getPicture : FAIL");
                                }
                            });
                        }
                    }
                }.start();
            }
        }
    }

}
