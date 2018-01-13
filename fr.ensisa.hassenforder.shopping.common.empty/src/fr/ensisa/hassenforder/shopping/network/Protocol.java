package fr.ensisa.hassenforder.shopping.network;

public interface Protocol {

    public static final int PORT_ID = 9876;

    public static final int REQUEST_CATEGORIES =    0x2001;
    public static final int REQUEST_PRODUCTS =      0x2002;
    public static final int REQUEST_SHOP_DETAILS =  0x2003;
    public static final int REQUEST_SHELF_DETAILS = 0x2004;
    public static final int REQUEST_PICTURE =       0x2005;

    public static final int REPLY_OK =              0x1001;
    public static final int REPLY_KO =              0x1002;
    public static final int REPLY_CATEGORIES =      0x1003;
    public static final int REPLY_PRODUCTS =        0x1004;
    public static final int REPLY_SHOP_DETAILS =    0x1005;
    public static final int REPLY_SHELF_DETAILS =   0x1006;
    public static final int REPLY_PICTURE =         0x1007;

}
