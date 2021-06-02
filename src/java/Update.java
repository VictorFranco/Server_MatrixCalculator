import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.json.simple.JSONArray;

public class Update extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException
    {
        response.setContentType("application/json;charset=UTF-8");
        String email= request.getParameter("email");
        String name= request.getParameter("nombre");
        String password= request.getParameter("password");
        String apellido= request.getParameter("apellido");
        String userSelected=request.getParameter("userSelected");
        HttpSession session=request.getSession();
        int resultado=0;
        
        String userName=(String)session.getAttribute("userName");
        String userEmail=(String)session.getAttribute("userEmail");
        JSONArray array=new JSONArray();
        if(userName!=null&&userEmail!=null)
            try{
                Class.forName("com.mysql.jdbc.Driver");
                Connection db = DriverManager.getConnection("jdbc:mysql://localhost:3306/Usuarios","root", "1234");
                Statement stmt = db.createStatement();

                boolean respuesta;
                ResultSet rs = stmt.executeQuery("SELECT * FROM Usuario where email = '" + email + "'");
                respuesta=rs.next();
                if(respuesta&&!rs.getString("idUsuario").equals(userSelected)){
                    db.close();
                }else{
                    PreparedStatement rss = db.prepareStatement("UPDATE Usuario SET nombre=? , apellido=? , password=? , email=? where idUsuario=?");
                    rss.setString(1,name);
                    rss.setString(2,apellido);
                    rss.setString(3,password);
                    rss.setString(4,email);
                    rss.setString(5,userSelected);
                    resultado=rss.executeUpdate();
                    db.close();
                }
            }
            catch(Exception e)
            {
                System.out.println("Error: "+e);
            }
        if(resultado==1){
            response.sendRedirect("ShowInfo");
        }
        PrintWriter out = response.getWriter();
        out.println(array);
        out.close();
    }
}
