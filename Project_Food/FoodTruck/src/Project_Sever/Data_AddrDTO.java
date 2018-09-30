package Project_Sever;

public class Data_AddrDTO {
	private String shopID;
	private Double shopAddr_x;
	private Double shopAddr_y;
	public Data_AddrDTO() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Data_AddrDTO(String shopID, Double shopAddr_x, Double shopAddr_y) {
		super();
		this.shopID = shopID;
		this.shopAddr_x = shopAddr_x;
		this.shopAddr_y = shopAddr_y;
	}
	public String getShopID() {
		return shopID;
	}
	public void setShopID(String shopID) {
		this.shopID = shopID;
	}
	public Double getShopAddr_x() {
		return shopAddr_x;
	}
	public void setShopAddr_x(Double shopAddr_x) {
		this.shopAddr_x = shopAddr_x;
	}
	public Double getShopAddr_y() {
		return shopAddr_y;
	}
	public void setShopAddr_y(Double shopAddr_y) {
		this.shopAddr_y = shopAddr_y;
	}
	@Override
	public String toString() {
		return "Data_AddrDTO [shopID=" + shopID + ", shopAddr_x=" + shopAddr_x + ", shopAddr_y=" + shopAddr_y + "]";
	}
	
	
}
