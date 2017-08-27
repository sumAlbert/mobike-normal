import identity.Bike;
import identity.Subway;
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

public class PutuoSubway extends HttpServlet {
    private final String DB_URL="jdbc:mysql://localhost:3306/ofo?useUnicode=true&characterEncoding=UTF-8&useSSL=true";
    private final String USER="root";
    private final String PW="123aaaaaa";
    private Connection connection;
    public PutuoSubway(){
        super();
        Driver driver= null;
        try {
            driver = new com.mysql.jdbc.Driver();
            DriverManager.registerDriver(driver);
            this.connection=DriverManager.getConnection(DB_URL,USER,PW);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException ,IOException{
        response.setHeader("Content-type", "text/html;charset=UTF-8");
        String num=request.getParameter("num");
        Logger logger=Logger.getLogger(Thread.currentThread().getStackTrace()[1].getClassName());
        logger.info("The num is "+num);
        if(num.equals("-1")){
            try {
                JSONArray jsonArray=new JSONArray();
                Statement statement=connection.createStatement();
                String sql="select * from putuo_subway_address";
                ResultSet resultSet=statement.executeQuery(sql);
                while (resultSet.next()){
                    String distX=resultSet.getString(2);
                    String distY=resultSet.getString(3);
                    String name=resultSet.getString(4);
                    Subway subway=new Subway(name,distX,distY);
                    jsonArray.add(subway);
                }
                String result=jsonArray.toString();
                PrintWriter printWriter=response.getWriter();
                printWriter.print(result);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        else{
            String message=request.getParameter("message");
            if(message.equals("normal")) {
                try {
                    HashMap<String, String> hashMap = new HashMap<>();
                    Integer integer = Integer.valueOf(num);
                    int intNum = integer.intValue();
                    Statement statement = connection.createStatement();
                    String sql = "select * from putuo_mobike_address3 where flag=\"170827_6\" limit " + String.valueOf(intNum) + ",1;";
                    ResultSet resultSet = statement.executeQuery(sql);
                    while (resultSet.next()) {
                        String distX = resultSet.getString("distX");
                        String distY = resultSet.getString("distY");
                        String id = resultSet.getString("bikeIds");
                        hashMap.put("distX", distX);
                        hashMap.put("distY", distY);
                        hashMap.put("id", id);
                    }
                    JSONObject jsonObject = JSONObject.fromObject(hashMap);
                    String result = jsonObject.toString();
                    PrintWriter printWriter = response.getWriter();
                    printWriter.print(result);
                } catch (Exception exception) {
                    exception.printStackTrace();
                }
            }
            else{
                String sub0="";
                String id=request.getParameter("id");
                String String_distances=request.getParameter("distances");
                String[] distances=String_distances.split(",");
                String sql_fix="insert into putuo_subway_distance (";
                for(int i=0;i<distances.length;i++){
                    String String_temp="sub"+String.valueOf(i);
                    if(i==distances.length-1){
                        sql_fix=sql_fix+String_temp;
                    }
                    else{
                        sql_fix=sql_fix+String_temp+",";
                    }
                }
                sql_fix=sql_fix+",id,flag) values (";
                for(int i=0;i<distances.length;i++){
                    String distance=distances[i];
                    String[] parts=distance.split("\\.");
                    String before=parts[0];
                    sql_fix=sql_fix+before+",";
                    if(i==0){
                        sub0=before;
                    }
                }
                sql_fix=sql_fix+"\'"+id+"\',\'170828_1\') on duplicate key update sub0 = "+sub0;
                try{
                    Statement statement=connection.createStatement();
                    statement.execute(sql_fix);
                    statement.close();
                }catch (Exception exception){
                    exception.printStackTrace();
                }
            }
        }
    }
    public void doPost(HttpServletRequest request,HttpServletResponse response) throws  ServletException, IOException{
        doGet(request,response);
    }
}
