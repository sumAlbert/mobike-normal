

import com.mysql.jdbc.Driver;
import net.sf.json.JSONObject;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.logging.Logger;

public class OneDayData extends HttpServlet{
    private String[] save_times={
            "2017-08-28 23:31:29",
            "2017-08-29 00:01:30",
            "2017-08-29 00:31:30",
            "2017-08-29 01:01:30",
            "2017-08-29 01:31:31",
            "2017-08-29 02:01:32",
            "2017-08-29 02:31:32",
            "2017-08-29 03:01:33",
            "2017-08-29 03:31:34",
            "2017-08-29 04:01:35",
            "2017-08-29 04:31:35",
            "2017-08-29 05:01:36",
            "2017-08-29 05:31:36",
            "2017-08-29 06:01:36",
            "2017-08-29 06:31:37",
            "2017-08-29 07:01:37",
            "2017-08-29 07:31:38",
            "2017-08-29 08:01:39",
            "2017-08-29 08:31:39",
            "2017-08-29 09:01:40",
            "2017-08-29 09:31:40",
            "2017-08-29 10:01:41",
            "2017-08-29 10:31:41",
            "2017-08-29 11:01:42",
            "2017-08-29 11:31:42",
            "2017-08-29 12:01:43",
            "2017-08-29 12:31:44",
            "2017-08-29 13:01:44",
            "2017-08-29 13:31:45",
            "2017-08-29 14:01:45",
            "2017-08-29 14:31:45",
            "2017-08-29 15:01:45",
            "2017-08-29 15:31:46",
            "2017-08-29 16:01:46",
            "2017-08-29 16:31:47",
            "2017-08-29 17:01:47",
            "2017-08-29 17:31:47",
            "2017-08-29 18:01:48",
            "2017-08-29 18:31:48",
            "2017-08-29 19:01:48",
            "2017-08-29 19:31:49",
            "2017-08-29 20:01:49",
            "2017-08-29 20:31:50",
            "2017-08-29 21:01:51",
            "2017-08-29 21:31:51",
            "2017-08-29 22:01:51",
            "2017-08-29 22:31:51",
            "2017-08-29 23:01:52",
            "2017-08-29 23:33:48",
    };
    private HashMap<String,Integer> result;
    private Double center_lng;
    private Double center_lat;
    private double lng_unit=(double)111000;
    private double lat_unit=(double)111000;
    private Connection connection;
    private Logger logger=Logger.getLogger(Thread.currentThread().getStackTrace()[0].getClassName());
    public OneDayData(){
        try{
            Driver driver=new com.mysql.jdbc.Driver();
            DriverManager.registerDriver(driver);
            connection=DriverManager.getConnection("jdbc:mysql://localhost:3306/ofo?useUnicode=true&characterEncoding=utf8&useSSL=true","root","123aaaaaa");
        }catch (Exception exception){
            exception.printStackTrace();
        }
    }
    protected void doPost(HttpServletRequest request, HttpServletResponse response){
        result=new HashMap<>();
        for (int i=0;i<save_times.length;i++){
            result.put(save_times[i],0);
        }
        DecimalFormat decimalFormat=new DecimalFormat("#.000");
        String lngBig=request.getParameter("lng");
        String latBig=request.getParameter("lat");
        center_lng=Double.parseDouble(decimalFormat.format(Double.parseDouble(lngBig)));
        center_lat=Double.parseDouble(decimalFormat.format(Double.parseDouble(latBig)));
        lat_unit=lng_unit*Math.cos(center_lat/180.0*Math.PI);
        String start_lng=String.valueOf(center_lng-0.005);
        String start_lat=String.valueOf(center_lat-0.005);
        String stop_lng=String.valueOf(center_lng+0.005);
        String stop_lat=String.valueOf(center_lat+0.005);
        try{
            Statement statement=connection.createStatement();
            String sql="select * from putuo_mobike_address3 where flag=\"170829_1\" and distX > "+(start_lng+"00000").substring(0,7)+" and distX < "+(stop_lng+"00000").substring(0,7)+" and distY > "+(start_lat+"00000").substring(0,6)+" and distY < "+(stop_lat+"00000").substring(0,6)+"";
            System.out.println(sql);
            ResultSet resultSet=statement.executeQuery(sql);
            int num=0;
            while(resultSet.next()){
                String distX=resultSet.getString("distX");
                String distY=resultSet.getString("distY");
                double distance=getDistance(distX,distY);
                if(distance<500){
                    String save_time=resultSet.getString("save_time").substring(0,19);
                    result.put(save_time,result.get(save_time)+1);
                }
                else{
                    System.out.println("false");
                }
                num++;
                logger.info(String.valueOf(num));
            }
            JSONObject jsonObject=JSONObject.fromObject(result);
            String json_Str=jsonObject.toString();
            PrintWriter printWriter=response.getWriter();
            printWriter.print(json_Str);
        }catch (Exception exception){
            exception.printStackTrace();
        }
    }
    protected void doGet(HttpServletRequest request, HttpServletResponse response){
        doPost(request,response);
    }
    protected double getDistance(String lng,String lat){
        double distance_x=lng_unit*Math.abs(Double.parseDouble(lng)-center_lng);
        double distance_y=lat_unit%Math.abs(Double.parseDouble(lat)-center_lat);
        double distance=Math.sqrt(Math.pow(distance_x,2)+Math.pow(distance_y,2));
        return distance;
    }
}
