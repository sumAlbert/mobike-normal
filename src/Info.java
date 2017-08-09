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

public class Info extends HttpServlet {
    private final String DB_URL="jdbc:mysql://localhost:3306/ofo?useUnicode=true&characterEncoding=UTF-8&useSSL=true";
    private final String USER="root";
    private final String PW="123aaaaaa";
    private Connection connection;
    private double start_lng;
    private double start_lat;
    private double stop_lng;
    private double stop_lat;
    private int handing_num = -1;
    private Logger logger = Logger.getLogger("Info");
    public Info(){
        super();
    }
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException ,IOException{
        getBorder();
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
    protected void getBorder(){
        try{
            Driver driver=new com.mysql.jdbc.Driver();
            DriverManager.registerDriver(driver);
            connection=DriverManager.getConnection(DB_URL,USER,PW);
            logger.info("ok");
        }catch (Exception exception){
            logger.warning("数据库链接问题");
            exception.printStackTrace();
        }
        try {
            int next_num = handing_num+1;
            Statement statement=connection.createStatement();
            String sql="select * from shanghai_border_bottom limit "+String.valueOf(next_num)+","+String.valueOf(next_num+1);
            ResultSet resultSet=statement.executeQuery(sql);
            while (resultSet.next()){
                start_lng=resultSet.getDouble("lng");
                start_lat=resultSet.getDouble("lat");
            }
            sql="select * from shanghai_border_upper limit "+String.valueOf(next_num)+","+String.valueOf(next_num+1);
            resultSet=statement.executeQuery(sql);
            while (resultSet.next()){
                stop_lng=resultSet.getDouble("lng");
                stop_lat=resultSet.getDouble("lat");
            }
        }catch (Exception exception){
            logger.warning("数据库取值失败");
            exception.printStackTrace();
        }
    }
}
