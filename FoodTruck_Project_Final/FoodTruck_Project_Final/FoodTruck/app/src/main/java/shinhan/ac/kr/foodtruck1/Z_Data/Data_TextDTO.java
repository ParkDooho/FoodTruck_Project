package shinhan.ac.kr.foodtruck1.Z_Data;

public class Data_TextDTO {
    private String shopID;
    private String shopName;
    private String shopMenu;
    private int shopPrice;
    private String shopEvent;

    public Data_TextDTO() {
        super();
        // TODO Auto-generated constructor stub
    }

    public Data_TextDTO(String shopID, String shopName, String shopMenu, int shopPrice, String shopEvent) {
        super();
        this.shopID = shopID;
        this.shopName = shopName;
        this.shopMenu = shopMenu;
        this.shopPrice = shopPrice;
        this.shopEvent = shopEvent;
    }

    public String getShopID() {
        return shopID;
    }

    public void setShopID(String shopID) {
        this.shopID = shopID;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public String getShopMenu() {
        return shopMenu;
    }

    public void setShopMenu(String shopMenu) {
        this.shopMenu = shopMenu;
    }

    public int getShopPrice() {
        return shopPrice;
    }

    public void setShopPrice(int shopPrice) {
        this.shopPrice = shopPrice;
    }

    public String getShopEvent() {
        return shopEvent;
    }

    public void setShopEvent(String shopEvent) {
        this.shopEvent = shopEvent;
    }

    @Override
    public String toString() {
        return "Data_TextDTO [shopID=" + shopID + ", shopName=" + shopName + ", shopMenu=" + shopMenu + ", shopPrice="
                + shopPrice + ", shopEvent=" + shopEvent + "]";
    }




}