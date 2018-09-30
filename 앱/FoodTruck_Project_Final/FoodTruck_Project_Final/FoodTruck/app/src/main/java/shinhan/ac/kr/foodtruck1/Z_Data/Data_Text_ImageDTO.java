package shinhan.ac.kr.foodtruck1.Z_Data;

public class Data_Text_ImageDTO {
    private String shopMenu;
    private int shopPrice;
    private String shopEvent;

    public Data_Text_ImageDTO() {
        super();
        // TODO Auto-generated constructor stub
    }

    public Data_Text_ImageDTO(String shopMenu, int shopPrice, String shopEvent) {
        super();
        this.shopMenu = shopMenu;
        this.shopPrice = shopPrice;
        this.shopEvent = shopEvent;
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
        return "Data_TextDTO [shopMenu=" + shopMenu + ", shopPrice=" + shopPrice + ", shopEvent=" + shopEvent + "]";
    }




}