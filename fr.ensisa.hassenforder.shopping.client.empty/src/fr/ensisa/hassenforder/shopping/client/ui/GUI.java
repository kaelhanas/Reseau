package fr.ensisa.hassenforder.shopping.client.ui;

import java.awt.Color;
import java.awt.Component;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeSelectionModel;
import fr.ensisa.hassenforder.shopping.client.model.ICategory;
import fr.ensisa.hassenforder.shopping.client.model.Item;
import fr.ensisa.hassenforder.shopping.client.model.ModelListener;
import fr.ensisa.hassenforder.shopping.client.model.Product;

/**
 *
 * @author hassenforder
 */
public class GUI extends javax.swing.JFrame implements ModelListener {

    private GUIListener listener;
    
    /**
     * Creates new form GUI
     */
    public GUI() {
        initComponents();
        try { shopBackground.setIcon (new ImageIcon(ImageIO.read(new File ("./backgrounds", "shop.png")))); } catch (IOException ex) { }
        try { displayBackground.setIcon (new ImageIcon(ImageIO.read(new File ("./backgrounds", "display.png")))); } catch (IOException ex) { }
        DefaultMutableTreeNode rootNode = new DefaultMutableTreeNode("No Category");
        DefaultTreeModel treeModel = new DefaultTreeModel(rootNode);
        jCategories.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
        jCategories.addTreeSelectionListener(new TreeSelectionListener() {
            @Override
            public void valueChanged(TreeSelectionEvent e) {
                DefaultMutableTreeNode node = (DefaultMutableTreeNode) jCategories.getLastSelectedPathComponent();
                if (node == null) return;
                Object nodeInfo = node.getUserObject(); 
                if (nodeInfo instanceof String) {
                    if (listener != null) listener.onClickGetAllCategories(); 
                }
                if (nodeInfo instanceof ICategory) {
                    String path = ((ICategory) nodeInfo).getFullName();
                    if (path == null || path.isEmpty()) path = "/";
                    if (listener != null) listener.onClickGetProductsForCategory(path); 
                }
            }
        });
        jCategories.setModel(treeModel);
        jCategories.setShowsRootHandles(false);
        shopBackground.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseClicked(MouseEvent e) {
				super.mouseClicked(e);
				int x = e.getX() / 24;
				int y = e.getY() / 24;
				System.out.println("X="+x+" Y="+y);
			}

        });
    }

    public void setListener(GUIListener listener) {
        this.listener = listener;
    }
    
    public void notifyStatusChanged (String status) {
        jStatus.setText(status);
    }

    @Override
    public void notifyCaddyChanged(List<Item> caddy) {
        if (caddy.isEmpty()) {
            jCaddyContent.removeAll();
        } else {
            List<Item> addedItems = new ArrayList<>(caddy);
            List<CaddyItem> removedItems = new ArrayList<>();
            for (Component i : jCaddyContent.getComponents()) {
                if (!(i instanceof CaddyItem)) continue;
                CaddyItem ci = (CaddyItem) i;
                if (caddy.contains(ci.getItem())) addedItems.remove(ci.getItem());
                else removedItems.add(ci);
            }
            for (Item item : addedItems) {
                jCaddyContent.add (new CaddyItem(item, listener));
            }
            for (CaddyItem item : removedItems) {
                jCaddyContent.remove(item);
            }
        }
        jCaddyContent.revalidate();
        jCaddyContent.repaint();        
    }

    @Override
    public void notifyCaddyItemChanged(Item item) {
        for (Component i : jCaddyContent.getComponents()) {
            if (!(i instanceof CaddyItem)) continue;
            CaddyItem ci = (CaddyItem) i;
            if (ci.getItem() == item) ci.updateView();
        }
    }

    @Override
    public void notifyProductsChanged(List<Product> products) {
        if (products.isEmpty()) {
            jProducts.removeAll();
        } else {
            List<Product> addedProducts = new ArrayList<>(products);
            List<ProductItem> removedProducts = new ArrayList<>();
            for (Component i : jProducts.getComponents()) {
                if (!(i instanceof ProductItem)) continue;
                ProductItem pi = (ProductItem) i;
                if (products.contains(pi.getProduct())) addedProducts.remove(pi.getProduct());
                else removedProducts.add(pi);
            }
            for (Product p : addedProducts) {
                jProducts.add (new ProductItem(p, listener));
            }
            for (ProductItem p : removedProducts) {
                jProducts.remove(p);
            }
        }
        jProducts.revalidate();
        jProducts.repaint();        
    }

    private void fillTree (DefaultTreeModel treeModel, DefaultMutableTreeNode parent, ICategory category) {
        DefaultMutableTreeNode childNode = null;
        if (category.getName() != null && ! category.getName().equals("")) {
            childNode = new DefaultMutableTreeNode(category);
            treeModel.insertNodeInto(childNode, parent, parent.getChildCount());
        } else {
            childNode = parent;
        }
        if (category.getChildren() == null) return;
        for (ICategory c : category.getChildren().values()) {
            fillTree (treeModel, childNode, c);
        }
    }

    @Override
    public void notifyCategoriesChanged(ICategory categories) {
        DefaultMutableTreeNode rootNode = new DefaultMutableTreeNode(categories);
        DefaultTreeModel treeModel = new DefaultTreeModel(rootNode);
        fillTree (treeModel, rootNode, categories);
        jCategories.setModel(treeModel);
        treeModel.reload();
    }

    @Override
    public void notifyComponentChanged(JComponent component, Image bi) {
        if (component instanceof JLabel) {
            ((JLabel) component).setIcon(new ImageIcon(bi));
        }
    }

    private void jInShopActionPerformed(java.awt.event.MouseEvent evt) {                                            
        Object o = ((JComponent) evt.getSource()).getClientProperty("data");
        if (o instanceof Item) {
            if (listener != null) listener.onClickInShopDetails((Item) o);
        }
    }

    private java.awt.event.MouseListener labelActionListener = new java.awt.event.MouseAdapter() {
        @Override
        public void mouseReleased(MouseEvent e) {
            jInShopActionPerformed (e);
        }
    };
    
    @Override
    public void notifyCaddyInShopChanged(List<Item> caddy) {
        jShop.removeAll();
        for (Item i : caddy) {
            Product p = i.getProduct();
            JLabel shopLabel = new JLabel();
            switch (i.getAvailable()) {
            case Yes :
                shopLabel.setBorder(BorderFactory.createLineBorder(Color.GREEN, 1));
                break;
            case Tight:
                shopLabel.setBorder(BorderFactory.createLineBorder(Color.ORANGE, 1));
                break;
            case Hot:
                shopLabel.setBorder(BorderFactory.createLineBorder(Color.ORANGE, 2));
                break;
            case Not:
                shopLabel.setBorder(BorderFactory.createLineBorder(Color.RED, 3));
                break;
            }
            shopLabel.putClientProperty("data", i);
            shopLabel.addMouseListener(labelActionListener);
            if (listener != null) listener.onGetHalfPicture (shopLabel, p.getId());
            jShop.add(shopLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(p.getShopX()*24, p.getShopY()*24, 24, 24));
            jShop.setBorder(BorderFactory.createLineBorder(Color.red, 2));
        }
        jShop.add(shopBackground, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 739, 277));
        jShop.revalidate();
        jShop.repaint();        
        jDisplay.removeAll();
        jDisplay.add(displayBackground, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 100, 277));
        jDisplay.revalidate();
        jDisplay.repaint();        
    }

    @Override
    public void notifyCaddyInDisplayChanged(Item item) {
        jDisplay.removeAll();
        Product p = item.getProduct();
        if (listener != null) listener.onGetHalfPicture (displayItem, p.getId());
        jDisplay.add(displayItem, new org.netbeans.lib.awtextra.AbsoluteConstraints(p.getShelfZX()*24, p.getShelfZY()*24, 24, 24));
        jDisplay.add(displayBackground, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 100, 277));
        jDisplay.revalidate();
        jDisplay.repaint();        
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jCaddyValid = new javax.swing.JButton();
        jCaddyClear = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jCaddyContent = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jCategories = new javax.swing.JTree();
        jScrollPane3 = new javax.swing.JScrollPane();
        jProducts = new javax.swing.JPanel();
        jShop = new javax.swing.JPanel();
        shopBackground = new javax.swing.JLabel();
        jDisplay = new javax.swing.JPanel();
        displayBackground = new javax.swing.JLabel();
        displayItem = new javax.swing.JLabel();
        jStatus = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setPreferredSize(new java.awt.Dimension(283, 195));

        jLabel1.setText("Caddy");

        jCaddyValid.setLabel("valid");
        jCaddyValid.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCaddyValidActionPerformed(evt);
            }
        });

        jCaddyClear.setLabel("clear");
        jCaddyClear.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCaddyClearActionPerformed(evt);
            }
        });

        jScrollPane1.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        jScrollPane1.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        jScrollPane1.setMaximumSize(new java.awt.Dimension(270, 160));
        jScrollPane1.setMinimumSize(new java.awt.Dimension(270, 160));
        jScrollPane1.setPreferredSize(new java.awt.Dimension(270, 160));

        jCaddyContent.setLayout(new javax.swing.BoxLayout(jCaddyContent, javax.swing.BoxLayout.Y_AXIS));
        jScrollPane1.setViewportView(jCaddyContent);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addGap(18, 18, 18)
                        .addComponent(jCaddyValid)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jCaddyClear)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jCaddyValid)
                    .addComponent(jCaddyClear))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(16, Short.MAX_VALUE))
        );

        jScrollPane2.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        jScrollPane2.setViewportView(jCategories);

        jScrollPane3.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        jScrollPane3.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

        jProducts.setLayout(new javax.swing.BoxLayout(jProducts, javax.swing.BoxLayout.Y_AXIS));
        jScrollPane3.setViewportView(jProducts);

        jShop.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jShop.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        shopBackground.setText("jLabel2");
        jShop.add(shopBackground, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 739, 277));

        jDisplay.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jDisplay.setPreferredSize(new java.awt.Dimension(100, 277));
        jDisplay.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        displayBackground.setText("jLabel2");
        jDisplay.add(displayBackground, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 100, 277));

        displayItem.setText("jLabel2");
        jDisplay.add(displayItem, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 70, -1, -1));

        jStatus.setText("status");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(14, 14, 14)
                        .addComponent(jShop, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jDisplay, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 239, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 419, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jStatus, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(jScrollPane3, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 185, Short.MAX_VALUE)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jDisplay, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jShop, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jStatus)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jCaddyClearActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCaddyClearActionPerformed
        if (listener != null) listener.onClickClearCaddy();
    }//GEN-LAST:event_jCaddyClearActionPerformed

    private void jCaddyValidActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCaddyValidActionPerformed
        if (listener != null) listener.onClickValidCaddy();
    }//GEN-LAST:event_jCaddyValidActionPerformed
  
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel displayBackground;
    private javax.swing.JLabel displayItem;
    private javax.swing.JButton jCaddyClear;
    private javax.swing.JPanel jCaddyContent;
    private javax.swing.JButton jCaddyValid;
    private javax.swing.JTree jCategories;
    private javax.swing.JPanel jDisplay;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jProducts;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JPanel jShop;
    private javax.swing.JLabel jStatus;
    private javax.swing.JLabel shopBackground;
    // End of variables declaration//GEN-END:variables

}
