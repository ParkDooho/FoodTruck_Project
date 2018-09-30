package Project_Sever;

public class ShopDTO {
	private String shopID;
	private String shopPW;
	private String shopName;
	private String shopTel;
	private String shopOwner;
	private String shopOpenTime;
	private String shopOpenPlace;
	public ShopDTO() {
		super();
		// TODO Auto-generated constructor stub
	}
	public ShopDTO(String shopID, String shopPW, String shopName, String shopTel, String shopOwner, String shopOpenTime,
			String shopOpenPlace) {
		super();
		this.shopID = shopID;
		this.shopPW = shopPW;
		this.shopName = shopName;
		this.shopTel = shopTel;
		this.shopOwner = shopOwner;
		this.shopOpenTime = shopOpenTime;
		this.shopOpenPlace = shopOpenPlace;
	}
	public String getShopID() {
		return shopID;
	}
	public void setShopID(String shopID) {
		this.shopID = shopID;
	}
	public String getShopPW() {
		return shopPW;
	}
	public void setShopPW(String shopPW) {
		this.shopPW = shopPW;
	}
	public String getShopName() {
		return shopName;
	}
	public void setShopName(String shopName) {
		this.shopName = shopName;
	}
	public String getShopTel() {
		return shopTel;
	}
	public void setShopTel(String shopTel) {
		this.shopTel = shopTel;
	}
	public String getShopOwner() {
		return shopOwner;
	}
	public void setShopOwner(String shopOwner) {
		this.shopOwner = shopOwner;
	}
	public String getShopOpenTime() {
		return shopOpenTime;
	}
	public void setShopOpenTime(String shopOpenTime) {
		this.shopOpenTime = shopOpenTime;
	}
	public String getShopOpenPlace() {
		return shopOpenPlace;
	}
	public void setShopOpenPlace(String shopOpenPlace) {
		this.shopOpenPlace = shopOpenPlace;
	}
	@Override
	public String toString() {
		return "ShopDTO [shopID=" + shopID + ", shopPW=" + shopPW + ", shopName=" + shopName + ", shopTel=" + shopTel
				+ ", shopOwner=" + shopOwner + ", shopOpenTime=" + shopOpenTime + ", shopOpenPlace=" + shopOpenPlace
				+ "]";
	}
	
	
	
}
