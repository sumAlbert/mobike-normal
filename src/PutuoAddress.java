import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;
import java.sql.DriverManager;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

public class PutuoAddress extends HttpServlet {
    private final String DB_URL="jdbc:mysql://localhost:3306/ofo?useUnicode=true&characterEncoding=UTF-8&useSSL=true";
    private final String USER="root";
    private final String PW="123aaaaaa";
    private Connection connection;
    private double start_lng;
    private double start_lat;
    private double stop_lng;
    private double stop_lat;
    private Logger logger = Logger.getLogger("Info");
    public PutuoAddress(){
        super();
        try{
            Driver driver=new com.mysql.jdbc.Driver();
            DriverManager.registerDriver(driver);
            connection=DriverManager.getConnection(DB_URL,USER,PW);
            logger.info("ok");
        }catch (Exception exception){
            logger.warning("数据库链接问题");
            exception.printStackTrace();
        }
    }
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException ,IOException{
        String result=request.getParameter("result");
        int handing_num = Integer.parseInt(request.getParameter("num"));
        saveHanding(handing_num,result);
        getBorder(handing_num);
        request.getParameter("num");
        Map json_Map=new HashMap();
        json_Map.put("start_lng",start_lng);
        json_Map.put("start_lat",start_lat);
        json_Map.put("stop_lng",stop_lng);
        json_Map.put("stop_lat",stop_lat);
        JSONObject jsonObject=JSONObject.fromObject(json_Map);
        String json_str=jsonObject.toString();
        logger.info(json_str);
        PrintWriter printWriter=response.getWriter();
        printWriter.print(json_str);
    }
    public void doPost(HttpServletRequest request,HttpServletResponse response) throws  ServletException, IOException{
        doGet(request,response);
    }
    protected void saveHanding(int handing_num, String result){
        if(handing_num >= 0){
            try {
                JSONArray jsonArray=JSONArray.fromObject(result);
                for(int i=0;i<jsonArray.size();i++){
                    JSONObject jsonObject=jsonArray.getJSONObject(i);
                    String lng=jsonObject.getString("lng");
                    String lat=jsonObject.getString("lat");
                    String province = jsonObject.getString("province");
                    String city = jsonObject.getString("city");
                    String district = jsonObject.getString("district");
                    String street = jsonObject.getString("street");
                    String streetNumber = jsonObject.getString("streetNumber");
                    if(district.equals("普陀区")){
                        String sql="insert into putuo_address_info (lng,lat,province,city,district,street,streetNumber) values (\""+lng+"\",\""+lat+"\",\""+province+"\",\""+city+"\",\""+district+"\",\""+street+"\",\""+streetNumber+"\")";
                        System.out.println(sql);
                        Statement statement=connection.createStatement();
                        statement.execute(sql);
                        statement.close();
                    }
                }
            }catch (Exception exception){
                logger.warning("数据库存值失败");
                exception.printStackTrace();
            }
        }
    }
    protected void getBorder(int handing_num){
        start_lat=31.224;
        stop_lat=31.310;
        start_lng=121.330+handing_num*0.001;
        stop_lng=start_lng;
    }
}
