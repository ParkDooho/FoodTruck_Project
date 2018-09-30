package Project_Sever;



import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;


/**
 * UserDAO Class : Shop테이블의 Query 생성 매소드를 포함하는 Class
 * @author Location_based_services Server
 *
 */

public class UserDAO {
	private Connection con;

	public UserDAO(Connection con) {
		super();
		this.con = con;
	}
	

	@SuppressWarnings("unchecked")
	public JSONObject userLog(String userID, String userPW){
		JSONObject jsonMain = new JSONObject();
		JSONArray JArray = new JSONArray();
		String sql = "select userID, userPW from User where userID=? and userPW=?";
		try {
			PreparedStatement pstmt = con.prepareStatement(sql);
			pstmt.setString(1,userID.replace("\r\n", "") );	
			pstmt.setString(2,userPW.replace("\r\n", "") );	
			ResultSet rs = pstmt.executeQuery();
			int count = 0;
			while(rs.next()) {
				UserDTO temp = new UserDTO();
				JSONObject jObject = new JSONObject();
				temp.setUserID(rs.getString("userID"));
				temp.setUserPW(rs.getString("userPW"));
				
				jObject.put("userID", temp.getUserID());
				jObject.put("userPW", temp.getUserPW());
				JArray.add(count,jObject);
				count++;
			}
			jsonMain.put("success", JArray);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return jsonMain;
	}
	@SuppressWarnings("unchecked")
	public JSONObject shopLog(String shopID, String shopPW){
		JSONObject jsonMain = new JSONObject();
		JSONArray JArray = new JSONArray();
		String sql = "select shopID, shopPW from Shop where shopID=? and shopPW=?";
		try {
			PreparedStatement pstmt = con.prepareStatement(sql);
			pstmt.setString(1,shopID.replace("\r\n", "") );	
			pstmt.setString(2,shopPW.replace("\r\n", "") );	
			ResultSet rs = pstmt.executeQuery();
			int count = 0;
			while(rs.next()) {
				ShopDTO temp = new ShopDTO();
				JSONObject jObject = new JSONObject();
				temp.setShopID(rs.getString("shopID"));
				temp.setShopPW(rs.getString("shopPW"));

				jObject.put("shopID", temp.getShopID());
				jObject.put("shopPW", temp.getShopPW());
				JArray.add(count,jObject);
				count++;
			}
			jsonMain.put("success", JArray);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return jsonMain;
	}
	
	@SuppressWarnings("unchecked")
	public JSONObject userCheck(String userID){
		JSONObject jsonMain = new JSONObject();
		JSONArray JArray = new JSONArray();
		String sql = "select userID from User where userID=?";
		try {
			PreparedStatement pstmt = con.prepareStatement(sql);
			pstmt.setString(1,userID.replace("\r\n", "") );	
			ResultSet rs = pstmt.executeQuery();
			int count = 0;
			while(rs.next()) {
				UserDTO temp = new UserDTO();
				JSONObject jObject = new JSONObject();
				temp.setUserID(rs.getString("userID"));
				
				jObject.put("userID", temp.getUserID());
				JArray.add(count,jObject);
				count++;
			}
			jsonMain.put("success", JArray);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return jsonMain;
	}
	@SuppressWarnings("unchecked")
	public JSONObject shopCheck(String shopID){
		JSONObject jsonMain = new JSONObject();
		JSONArray JArray = new JSONArray();
		String sql = "select shopID, shopPW from Shop where shopID=?";
		try {
			PreparedStatement pstmt = con.prepareStatement(sql);
			pstmt.setString(1,shopID.replace("\r\n", "") );	
			ResultSet rs = pstmt.executeQuery();
			int count = 0;
			while(rs.next()) {
				ShopDTO temp = new ShopDTO();
				JSONObject jObject = new JSONObject();
				temp.setShopID(rs.getString("shopID"));

				jObject.put("shopID", temp.getShopID());
				JArray.add(count,jObject);
				count++;
			}
			jsonMain.put("success", JArray);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return jsonMain;
	}
	
	@SuppressWarnings("unchecked")
	public JSONObject selectData_text_Menu(String shopMenu){
		JSONObject jsonMain = new JSONObject();
		JSONArray JArray = new JSONArray();
		String sql = "Select shopID, shopName, shopMenu, shopPrice, shopEvent from Data_Text where shopMenu=?";
		try {
			PreparedStatement pstmt = con.prepareStatement(sql);			
			pstmt.setString(1,shopMenu.replace("\r\n", "") );			
			ResultSet rs = pstmt.executeQuery();			
			int count =0;
			while(rs.next()) {
				Data_TextDTO temp = new Data_TextDTO();
				JSONObject jObject = new JSONObject();
				temp.setShopID(rs.getString("shopID"));
				temp.setShopName(rs.getString("shopName"));
				temp.setShopMenu(rs.getString("shopMenu"));
				temp.setShopPrice(rs.getInt("shopPrice"));
				temp.setShopEvent(rs.getString("shopEvent"));

				jObject.put("shopID", temp.getShopID());
				jObject.put("shopName", temp.getShopName());
				jObject.put("shopMenu", temp.getShopMenu());
				jObject.put("shopPrice", temp.getShopPrice());
				jObject.put("shopEvent", temp.getShopEvent());
				JArray.add(count,jObject);
				count++;
			}
			jsonMain.put("success", JArray);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return jsonMain;
	}
	

	@SuppressWarnings("unchecked")
	public JSONObject select_Menu(String shopID){
		JSONObject jsonMain = new JSONObject();
		JSONArray JArray = new JSONArray();
		String sql = "Select shopMenu, shopPrice, shopEvent from Data_Text where shopID=?";
		try {
			PreparedStatement pstmt = con.prepareStatement(sql);			
			pstmt.setString(1,shopID.replace("\r\n", "") );			
			ResultSet rs = pstmt.executeQuery();			
			int count =0;
			while(rs.next()) {
				Data_Text_ImageDTO temp = new Data_Text_ImageDTO();
				JSONObject jObject = new JSONObject();
				temp.setShopMenu(rs.getString("shopMenu"));
				temp.setShopPrice(rs.getInt("shopPrice"));
				temp.setShopEvent(rs.getString("shopEvent"));

		
				jObject.put("shopMenu", temp.getShopMenu());
				jObject.put("shopPrice", temp.getShopPrice());
				jObject.put("shopEvent", temp.getShopEvent());
				JArray.add(count,jObject);
				count++;
			}
			jsonMain.put("success", JArray);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return jsonMain;
	}
	
	@SuppressWarnings("unchecked")
	public JSONObject selectData_text_Name(String shopName){
		JSONObject jsonMain = new JSONObject();
		JSONArray JArray = new JSONArray();
		String sql = "Select shopID, shopName, shopMenu, shopPrice, shopEvent from Data_Text where shopName=?";
		try {
			PreparedStatement pstmt = con.prepareStatement(sql);			
			pstmt.setString(1,shopName.replace("\r\n", "") );	
			ResultSet rs = pstmt.executeQuery();			
			int count =0;
			while(rs.next()) {
				Data_TextDTO temp = new Data_TextDTO();
				JSONObject jObject = new JSONObject();
				temp.setShopID(rs.getString("shopID"));
				temp.setShopName(rs.getString("shopName"));
				temp.setShopMenu(rs.getString("shopMenu"));
				temp.setShopPrice(rs.getInt("shopPrice"));
				temp.setShopEvent(rs.getString("shopEvent"));

				jObject.put("shopID", temp.getShopID());
				jObject.put("shopName", temp.getShopName());
				jObject.put("shopMenu", temp.getShopMenu());
				jObject.put("shopPrice", temp.getShopPrice());
				jObject.put("shopEvent", temp.getShopEvent());
				JArray.add(count,jObject);
				count++;
			}
			jsonMain.put("success", JArray);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return jsonMain;
	}
	
	/////////////////////////////////////////////////////////////

	
	@SuppressWarnings("unchecked")
	public JSONObject shop_Information(String shopID){
		JSONObject jsonMain = new JSONObject();
		JSONArray JArray = new JSONArray();
		String sql = "select * from Shop where shopID=?";
		try {
			PreparedStatement pstmt = con.prepareStatement(sql);
			pstmt.setString(1,shopID.replace("\r\n", "") );	
			ResultSet rs = pstmt.executeQuery();
			int count = 0;
			while(rs.next()) {
				ShopDTO temp = new ShopDTO();
				JSONObject jObject = new JSONObject();
				temp.setShopID(rs.getString("shopID"));
				temp.setShopName(rs.getString("shopName"));
				temp.setShopTel(rs.getString("shopTel"));
				temp.setShopOwner(rs.getString("shopOwner"));
				temp.setShopOpenTime(rs.getString("shopOpenTime"));
				temp.setShopOpenPlace(rs.getString("shopOpenPlace"));

				jObject.put("shopID", temp.getShopID());
				jObject.put("shopName", temp.getShopName());
				jObject.put("shopTel", temp.getShopTel());
				jObject.put("shopOwner", temp.getShopOwner());
				jObject.put("shopOpenTime", temp.getShopOpenTime());
				jObject.put("shopOpenPlace", temp.getShopOpenPlace());
				JArray.add(count,jObject);
				count++;
			}
			jsonMain.put("success", JArray);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return jsonMain;
	}
	
	@SuppressWarnings("unchecked")
	public JSONObject shop_OpenSelect(String shopID){
		JSONObject jsonMain = new JSONObject();
		JSONArray JArray = new JSONArray();
		String sql = "select * from Shop where shopID=?";
		try {
			PreparedStatement pstmt = con.prepareStatement(sql);
			pstmt.setString(1,shopID.replace("\r\n", "") );	
			ResultSet rs = pstmt.executeQuery();
			int count = 0;
			while(rs.next()) {
				ShopDTO temp = new ShopDTO();
				JSONObject jObject = new JSONObject();
				temp.setShopOpenTime(rs.getString("shopOpenTime"));
				temp.setShopOpenPlace(rs.getString("shopOpenPlace"));

				jObject.put("shopOpenTime", temp.getShopOpenTime());
				jObject.put("shopOpenPlace", temp.getShopOpenPlace());
				JArray.add(count,jObject);
				count++;
			}
			jsonMain.put("success", JArray);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return jsonMain;
	}
	

	public void  shop_OpenUpdate(String shopOpenTime,String shopOpenPlace,String shopID) {
		String sql = "update shop set shopOpenTime=?,shopOpenPlace=? where shopID=?";		
		try {
			PreparedStatement pstmt = con.prepareStatement(sql);
			pstmt.setString(1,shopOpenTime);
			pstmt.setString(2,shopOpenPlace);
			pstmt.setString(3,shopID);
			
			pstmt.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	
	@SuppressWarnings("unchecked")
	public JSONObject shop_MenuSelect(String shopID, String shopMenu){
		JSONObject jsonMain = new JSONObject();
		JSONArray JArray = new JSONArray();
		String sql = "select shopMenu,shopPrice,shopEvent from Data_Text where shopID=? and shopMenu=?";
		try {
			PreparedStatement pstmt = con.prepareStatement(sql);
			pstmt.setString(1,shopID.replace("\r\n", "") );	
			pstmt.setString(2,shopMenu.replace("\r\n", "") );	
			ResultSet rs = pstmt.executeQuery();
			int count = 0;
			while(rs.next()) {
				Data_Text_ImageDTO temp = new Data_Text_ImageDTO();
				JSONObject jObject = new JSONObject();
				temp.setShopMenu(rs.getString("shopMenu"));
				temp.setShopPrice(rs.getInt("shopPrice"));
				temp.setShopEvent(rs.getString("shopEvent"));

				jObject.put("shopMenu", temp.getShopMenu());
				jObject.put("shopPrice", temp.getShopPrice());
				jObject.put("shopEvent", temp.getShopEvent());
				JArray.add(count,jObject);
				count++;
			}
			jsonMain.put("success", JArray);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return jsonMain;
	}
	
	
	
	public void  shopInsert(String shopID, String shopPW, String shopName, String shopTel, String shopOwner) {
		String sql = "insert into shop values(?,?,?,?,?,?,?)";		
		try {
			PreparedStatement pstmt = con.prepareStatement(sql);
			pstmt.setString(1,shopID);
			pstmt.setString(2,shopPW);
			pstmt.setString(3,shopName);
			pstmt.setString(4,shopTel);
			pstmt.setString(5,shopOwner);
			pstmt.setString(6,"");
			pstmt.setString(7,"");
			pstmt.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	public void  userInsert(String userID, String userPW, String userName, String userTel, String userNum) {
		String sql = "insert into user values(?,?,?,?,?)";		
		try {
			PreparedStatement pstmt = con.prepareStatement(sql);
			pstmt.setString(1, userID);
			pstmt.setString(2, userPW);
			pstmt.setString(3, userName);
			pstmt.setString(4, userTel);
			pstmt.setString(5, userNum);
			pstmt.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	public void  insertData_text(String shopID, String shopName, String shopMenu, int shopPrice, String shopEvent) {
		String sql = "insert into Data_Text values(?,?,?,?,?)";		
		try {
			PreparedStatement pstmt = con.prepareStatement(sql);
			pstmt.setString(1,shopID);
			pstmt.setString(2,shopName);
			pstmt.setString(3,shopMenu);
			pstmt.setInt(4,shopPrice);
			pstmt.setString(5,shopEvent);
			pstmt.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	

	public void  deleteData_text(String shopID, String shopMenu) {
		String sql = "delete from Data_Text where shopID=? and shopMenu=?";		
		try {
			PreparedStatement pstmt = con.prepareStatement(sql);
			pstmt.setString(1, shopID);
			pstmt.setString(2, shopMenu);
			pstmt.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
	}
	
	//////////////////////////////////////////////////////////////
	
	public void  insertShop_GPS(String shopID, Double shopAddr_x, Double shopAddr_y) {
		String sql = "insert into Data_Addr values(?,?,?)";		
		try {
			PreparedStatement pstmt = con.prepareStatement(sql);
			pstmt.setString(1, shopID);
			pstmt.setDouble(2, shopAddr_x);
			pstmt.setDouble(3,shopAddr_y);
			pstmt.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	

	@SuppressWarnings("unchecked")
	public JSONObject selectShop_GPS(Double userAddr_x, Double userAddr_y){
		JSONObject jsonMain = new JSONObject();
		JSONArray JArray = new JSONArray();
		String sql = "Select * from Data_Addr";
		try {
			PreparedStatement pstmt = con.prepareStatement(sql);
			ResultSet rs = pstmt.executeQuery();			
			int count =0;
			while(rs.next()) {
				double dLat=Math.toRadians(rs.getDouble("shopAddr_x") - userAddr_x );
				double dLng=Math.toRadians(rs.getDouble("shopAddr_y") - userAddr_y );
				double result_a=Math.sin(dLat/2) * Math.sin(dLat/2) + Math.cos(Math.toRadians(userAddr_x)) *
			               Math.cos(Math.toRadians(rs.getDouble("shopAddr_x"))) * Math.sin(dLng/2) * Math.sin(dLng/2);
				double result_b=2 * Math.atan2(Math.sqrt(result_a), Math.sqrt(1-result_a));
				double dist = (double)6374000*result_b;
				if(dist<2000) {
				Data_AddrDTO temp = new Data_AddrDTO();
				JSONObject jObject = new JSONObject();				
				temp.setShopID(rs.getString("shopID"));
				temp.setShopAddr_x(rs.getDouble("shopAddr_x"));
				temp.setShopAddr_y(rs.getDouble("shopAddr_y"));
				
				jObject.put("shopID", temp.getShopID());
				jObject.put("shopAddr_x", temp.getShopAddr_x());
				jObject.put("shopAddr_y", temp.getShopAddr_y());
				JArray.add(count,jObject);
				count++;}
			}
			jsonMain.put("success", JArray);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return jsonMain;
	}
	
	
	public void  deleteShop_GPS(String shopID) {
		String sql = "delete from Data_Addr where shopID=?";		
		try {
			PreparedStatement pstmt = con.prepareStatement(sql);
			pstmt.setString(1, shopID);
			pstmt.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
	}
//////////////////////////////////////////////////////////////
	
	public void  insertShop_Image(String shopID, String shopPic) {
		String sql = "insert into Data_Picture values(?,?)";
		try {
			PreparedStatement pstmt = con.prepareStatement(sql);
			pstmt.setString(1, shopID);
			pstmt.setString(2, shopPic);
			pstmt.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
	}
	
	
	@SuppressWarnings("unchecked")
	public JSONObject selectShop_ImageData(String shopID) {
		JSONObject jsonMain = new JSONObject();
		JSONArray JArray = new JSONArray();
	
		String sql = "Select shopPic from Data_Picture where shopID=?";
		try {
			PreparedStatement pstmt = con.prepareStatement(sql);			
			pstmt.setString(1,shopID.replace("\r\n", "") );			
			ResultSet rs = pstmt.executeQuery();			
			int count =0;
			while(rs.next()) {
				Data_PictureDTO temp = new Data_PictureDTO();
				JSONObject jObject = new JSONObject();
				temp.setShopPic(rs.getString("shopPic"));
			
				jObject.put("shopPic", temp.getShopPic());
				JArray.add(count,jObject);
				count++;
			}
			jsonMain.put("success", JArray);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return jsonMain;
	
	
	}
	

	public void  deleteShop_ImageData(String shopID) {
		String sql = "delete from Data_Picture where shopID=?";		
		try {
			PreparedStatement pstmt = con.prepareStatement(sql);
			pstmt.setString(1, shopID);
			pstmt.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
	}
	
	
}
	
	
	
	
	




