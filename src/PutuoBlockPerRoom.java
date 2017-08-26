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

public class PutuoBlockPerRoom extends HttpServlet {
    private final String DB_URL="jdbc:mysql://localhost:3306/ofo?useUnicode=true&characterEncoding=UTF-8&useSSL=true";
    private final String USER="root";
    private final String PW="123aaaaaa";
    private Connection connection;
    private double start_lng;
    private double start_lat;
    private double stop_lng;
    private double stop_lat;
    private Logger logger = Logger.getLogger("Info");
    public PutuoBlockPerRoom(){
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
        int count=0;
        String sql="select * from putuo_mobike_address2 where save_time = '2017-08-13 02:00:00'";
        String sql1="SELECT * FROM putuo_xiaoqu_info where room is not null";
        Map<String,Integer> map=new HashMap<>();
        Map<String,Integer> room_map=new HashMap<>();
        try {
            Statement statement=connection.createStatement();
            ResultSet resultSet=statement.executeQuery(sql);
            while (resultSet.next()){
                StringBuffer stringBuffer=new StringBuffer(0);
                Logger logger=Logger.getLogger(Thread.currentThread().getStackTrace()[1].getClassName());
                String lng_str=lngHash(resultSet.getString("distX"));
                String lat_str=latHash(resultSet.getString("distY"));
                stringBuffer.append(lng_str);
                stringBuffer.append(lat_str);
                String key=stringBuffer.toString();
                if(map.containsKey(key)){
                    Integer value_Int=map.get(key);
                    int value_int=value_Int.intValue();
                    value_int+=1;
                    map.put(key,new Integer(value_int+1));
                }
                else{
                    map.put(key,new Integer(1));
                }
            }
            resultSet=statement.executeQuery(sql1);
            while (resultSet.next()){
                StringBuffer stringBuffer=new StringBuffer(0);
                Logger logger=Logger.getLogger(Thread.currentThread().getStackTrace()[1].getClassName());
                String lng_str=lngHash(resultSet.getString("lng"));
                String lat_str=latHash(resultSet.getString("lat"));
                int room=resultSet.getInt("room");
                stringBuffer.append(lng_str);
                stringBuffer.append(lat_str);
                String key=stringBuffer.toString();
                if(map.containsKey(key)){
                    Integer value_Int=map.get(key);
                    int value_int=value_Int.intValue();
                    value_int+=1;
                    room_map.put(key,new Integer(value_int+room));
                }
                else{
                    room_map.put(key,new Integer(room));
                }
            }
            JSONObject jsonObject=JSONObject.fromObject(room_map);
            String json_str=jsonObject.toString();
            PrintWriter printWriter=response.getWriter();
            printWriter.print(json_str);
        }catch (Exception exception){
            exception.printStackTrace();
        }
    }
    public void doPost(HttpServletRequest request,HttpServletResponse response) throws  ServletException, IOException{
        doGet(request,response);
    }
    private static String lngHash(String lng){
        String result="";
        String key_str=lng.substring(4,6);
        int key_int=Integer.parseInt(key_str)+32;
        char key_char=(char)key_int;
        result+=key_char;
        return result;
    }
    private static String latHash(String lat){
        String result="";
        String key_str=lat.substring(3,5);
        int key_int=Integer.parseInt(key_str)+26;
        char key_char=(char)key_int;
        result+=key_char;
        return result;
    }
}
