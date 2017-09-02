

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

public class LujiazuiOneDay extends HttpServlet{
    private String[] save_times={
            "2017-08-30 00:00:51",
            "2017-08-30 00:30:51",
            "2017-08-30 01:00:52",
            "2017-08-30 01:30:52",
            "2017-08-30 02:00:52",
            "2017-08-30 02:30:53",
            "2017-08-30 03:00:54",
            "2017-08-30 03:30:54",
            "2017-08-30 04:00:55",
            "2017-08-30 04:30:55",
            "2017-08-30 05:00:56",
            "2017-08-30 05:30:56",
            "2017-08-30 06:00:57",
            "2017-08-30 06:30:58",
            "2017-08-30 07:00:58",
            "2017-08-30 07:30:58",
            "2017-08-30 08:00:59",
            "2017-08-30 08:30:59",
            "2017-08-30 09:01:00",
            "2017-08-30 09:31:00",
            "2017-08-30 10:01:01",
            "2017-08-30 10:31:02",
            "2017-08-30 11:01:02",
            "2017-08-30 11:31:03",
            "2017-08-30 12:01:03",
            "2017-08-30 12:31:03",
            "2017-08-30 13:01:03",
            "2017-08-30 13:31:04",
            "2017-08-30 14:01:04",
            "2017-08-30 14:31:04",
            "2017-08-30 15:01:05",
            "2017-08-30 15:31:06",
            "2017-08-30 16:01:06",
            "2017-08-30 16:31:06",
            "2017-08-30 17:01:07",
            "2017-08-30 17:31:08",
            "2017-08-30 18:01:08",
            "2017-08-30 18:31:09",
            "2017-08-30 19:01:10",
            "2017-08-30 19:31:11",
            "2017-08-30 20:01:12",
            "2017-08-30 20:31:12",
            "2017-08-30 21:01:12",
            "2017-08-30 21:31:12",
            "2017-08-30 22:01:13",
            "2017-08-30 22:31:13",
            "2017-08-30 23:01:14",
            "2017-08-30 23:31:14",
            "2017-08-31 00:01:15"
    };
    private HashMap<String,Integer> result;
    private Double center_lng;
    private Double center_lat;
    private double lng_unit=(double)111000;
    private double lat_unit=(double)111000;
    private Connection connection;
    private Logger logger=Logger.getLogger(Thread.currentThread().getStackTrace()[0].getClassName());
    public LujiazuiOneDay(){
        try{
            Driver driver=new Driver();
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
            String sql="select * from putuo_mobike_address5 where flag=\"170830_2\" and distX > "+(start_lng+"00000").substring(0,7)+" and distX < "+(stop_lng+"00000").substring(0,7)+" and distY > "+(start_lat+"00000").substring(0,6)+" and distY < "+(stop_lat+"00000").substring(0,6)+"";
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
                    System.out.println("false ");
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
