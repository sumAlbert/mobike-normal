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
    }
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException ,IOException{
    }
    public void doPost(HttpServletRequest request,HttpServletResponse response) throws  ServletException, IOException{
        doGet(request,response);
    }
}
