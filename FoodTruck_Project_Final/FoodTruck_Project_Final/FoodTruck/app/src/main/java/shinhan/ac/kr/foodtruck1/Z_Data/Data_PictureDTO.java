package shinhan.ac.kr.foodtruck1.Z_Data;

public class Data_PictureDTO {
    private String shopID;
    private String shopPic;


    public Data_PictureDTO() {
        super();
        // TODO Auto-generated constructor stub
    }


    public Data_PictureDTO(String shopID, String shopPic) {
        super();
        this.shopID = shopID;
        this.shopPic = shopPic;
    }


    public String getShopID() {
        return shopID;
    }


    public void setShopID(String shopID) {
        this.shopID = shopID;
    }


    public String getShopPic() {
        return shopPic;
    }


    public void setShopPic(String shopPic) {
        this.shopPic = shopPic;
    }


    @Override
    public String toString() {
        return "Data_PictureDTO [shopID=" + shopID + ", shopPic=" + shopPic + "]";
    }



}
