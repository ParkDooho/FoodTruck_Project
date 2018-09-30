<%@page import="java.io.OutputStream"%>
<%@page import="java.io.File"%>
<%@page import="java.io.FileInputStream"%>
<%@page import="java.io.BufferedInputStream"%>
<%@page import="java.io.IOException"%>
<%@page import="java.util.Enumeration"%>
<%@page import="com.oreilly.servlet.multipart.DefaultFileRenamePolicy"%>
<%@page import="com.oreilly.servlet.MultipartRequest"%>
<%@page import="org.json.simple.JSONArray"%>
<%@page import="org.json.simple.JSONObject"%>
<%@page import="java.sql.*"%>
<%@page import="Project_Sever.*"%>
<%@page import="org.json.simple.*"%>
<%@page import="java.net.URLEncoder"%>
<%@page import="java.net.URLDecoder"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
   pageEncoding="UTF-8"%>
<%
   request.setCharacterEncoding("UTF-8");
%>

<%
   Connection con = DBConnection.openConnection();
   UserDAO U_DB = new UserDAO(con);
   JSONObject jsonObj = new JSONObject();
   JSONArray jsonArr = new JSONArray();
   String action = request.getParameter("action");
   String result="";
   String userID, userPW, userName, userTel, userNum, shopID, shopPW, shopName, shopTel, shopOwner, shopMenu, shopEvent, shopPic,shopOpenPlace,shopOpenTime;
   int shopPrice=0;
   //좌표관련 함수
   double userAddr_x=0,userAddr_y=0,shopAddr_x=0, shopAddr_y=0;
   //이미지관련 함수
   String fileName;
   
      
      
   
   switch (action) {
   case "userLog":
      userID = request.getParameter("userID");
      userPW = request.getParameter("userPW");
      jsonObj = U_DB.userLog(userID, userPW);
      out.println(jsonObj.toJSONString());
      break;
   case "userCheck":
	      userID = request.getParameter("userID");
	      jsonObj = U_DB.userCheck(userID);
	      out.println(jsonObj.toJSONString());
	      break;
   case "userInsert":      
      userID = request.getParameter("userID");
      userPW = request.getParameter("userPW");
      userName = request.getParameter("userName");
      userTel = request.getParameter("userTel");
      userNum = request.getParameter("userNum");
      U_DB.userInsert(userID, userPW, userName, userTel, userNum);
      break;
   case "shopLog":
      shopID = request.getParameter("shopID");
      shopPW = request.getParameter("shopPW");
      jsonObj = U_DB.shopLog(shopID,shopPW);
      out.println(jsonObj.toJSONString());
      break;
   case "shopCheck":
	      shopID = request.getParameter("shopID");
	      jsonObj = U_DB.shopCheck(shopID);
	      out.println(jsonObj.toJSONString());
	      break;
   case "shopInsert":
      shopID = request.getParameter("shopID");
      shopPW = request.getParameter("shopPW");
      shopName = request.getParameter("shopName");
      shopTel = request.getParameter("shopTel");
      shopOwner = request.getParameter("shopOwner");
      U_DB.shopInsert(shopID,shopPW,shopName,shopTel,shopOwner);
      break;
   case "selectData_text_Menu":
         shopMenu = request.getParameter("shopMenu");
         jsonObj = U_DB.selectData_text_Menu(shopMenu);
         out.println(jsonObj.toJSONString());
         break;
   case "selectData_text_Name":
         shopName = request.getParameter("shopName");
         jsonObj = U_DB.selectData_text_Name(shopName);
         out.println(jsonObj.toJSONString());
         break;
   case "shop_Information":
	   shopID = request.getParameter("shopID");
       jsonObj = U_DB.shop_Information(shopID);
       out.println(jsonObj.toJSONString());
       break;
   case "shop_OpenSelect":
	   shopID = request.getParameter("shopID");
       jsonObj = U_DB.shop_OpenSelect(shopID);
       out.println(jsonObj.toJSONString());
	   break;
   case "shop_OpenUpdate":
	   shopOpenTime = request.getParameter("shopOpenTime");
	   shopOpenPlace = request.getParameter("shopOpenPlace");
	   shopID = request.getParameter("shopID");
	   U_DB.shop_OpenUpdate(shopOpenTime, shopOpenPlace, shopID);
	   break;
   case "shop_MenuSelect":
	   shopID = request.getParameter("shopID");
	   shopMenu = request.getParameter("shopMenu");
       jsonObj = U_DB.shop_MenuSelect(shopID,shopMenu);
       out.println(jsonObj.toJSONString());
	   break;
   case "insertData_text":
      shopID = request.getParameter("shopID");
      shopName = request.getParameter("shopName");
      shopMenu = request.getParameter("shopMenu");
      shopPrice = Integer.parseInt(request.getParameter("shopPrice"));
      shopEvent = request.getParameter("shopEvent");
      U_DB.insertData_text(shopID, shopName, shopMenu, shopPrice, shopEvent);
      break;    
   case "deleteData_text":
	      shopID = request.getParameter("shopID");
	      shopMenu = request.getParameter("shopMenu");;
	      U_DB.deleteData_text(shopID,shopMenu);
	      break;      
   case "select_Menu":
	   shopID = request.getParameter("shopID");
	   jsonObj = U_DB.select_Menu(shopID);
       out.println(jsonObj.toJSONString());
	   break;
   case "insertShop_GPS":
      shopID = request.getParameter("shopID");
      if(request.getParameter("shopAddr_x")!=null && request.getParameter("shopAddr_x")!="") {
      	shopAddr_x = Double.parseDouble(request.getParameter("shopAddr_x"));}
      if(request.getParameter("shopAddr_y")!=null && request.getParameter("shopAddr_y")!="") {
      	shopAddr_y = Double.parseDouble(request.getParameter("shopAddr_y"));}
      U_DB.insertShop_GPS(shopID,shopAddr_x ,shopAddr_y);
      break;
   case "selectShop_GPS":
	   if(request.getParameter("userAddr_x")!=null && request.getParameter("userAddr_x")!="") {
		userAddr_x = Double.parseDouble(request.getParameter("userAddr_x"));}
	   if(request.getParameter("userAddr_y")!=null && request.getParameter("userAddr_y")!="") {
		userAddr_y = Double.parseDouble(request.getParameter("userAddr_y"));}
	   jsonObj = U_DB.selectShop_GPS(userAddr_x,userAddr_y);
	   out.println(jsonObj.toJSONString());
	  break;
   case "deleteShop_GPS":
      shopID = request.getParameter("shopID");
      U_DB.deleteShop_GPS(shopID);
      break;
   case "insertShop_Image":
      shopID = request.getParameter("shopID");
      U_DB.deleteShop_ImageData(shopID);
      String dir ="C:/Project_Food/FoodTruck/WebContent/Image";
      int max = 5*1024*1024; //16MB

      try {
         MultipartRequest m = new MultipartRequest(request, dir, max); // 클라이언트에서 보낸 사진을 받는 부분입니다.   
         Enumeration files = m.getFileNames();

         //파일 정보가 있다면
         if(files.hasMoreElements()) {
         String name = (String)files.nextElement();
         fileName = m.getFilesystemName(name);
         System.out.println("이미지를 저장하였습니다. : " + fileName);
         shopPic =fileName;
         U_DB.insertShop_Image(shopID,shopPic);
         }              
                
         } catch (IOException e) {
            out.println("안드로이드 부터 이미지를 받아옵니다.");
         }
      break;
   case "selectShop_ImageData":
      shopID = request.getParameter("shopID");
      jsonObj = U_DB.selectShop_ImageData(shopID);
      out.println(jsonObj.toJSONString());
      break;
   case "selectShop_Image":
	      shopPic = request.getParameter("shopPic");
	      byte[] buffer = new byte[1024];
	      ServletOutputStream o = response.getOutputStream();
	      try{

	            String file = "C:/Project_Food/FoodTruck/WebContent/Image/"+shopPic;
	           BufferedInputStream in = new BufferedInputStream(new FileInputStream(file));
	            int n = 0;
	            while((n=in.read(buffer,0,1024))!=-1){o.write(buffer,0,n);}
	           o.close();
	           in.close();
	      }catch(Exception e){e.printStackTrace();}   
	      break;
   default:
      break;
   }
      
      
      
      
   DBConnection.closeConnection(con);

   
%>

