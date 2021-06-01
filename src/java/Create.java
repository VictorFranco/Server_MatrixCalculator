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

public class Create extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException
    {
        String email=request.getParameter("Email");
        String name=request.getParameter("Nombre");
        String password= request.getParameter("Password");
        String last_name= request.getParameter("Apellido");

        JSONArray array=new JSONArray();
        response.setContentType("application/json;charset=UTF-8");
        int resultado=0;
        try
        {
                Class.forName("com.mysql.jdbc.Driver");
                Connection db = DriverManager.getConnection("jdbc:mysql://localhost:3306/Usuarios","root", "1234");
                Statement stmt = db.createStatement();
                boolean respuesta;
                ResultSet rs = stmt.executeQuery("SELECT * FROM Usuario where email = '" + email + "'");
                respuesta=rs.next();
                if(respuesta){
                    System.out.println("Ese usuario ya existe");
                }else{
                    PreparedStatement rss = db.prepareStatement("INSERT INTO Usuario (email,nombre,apellido,password) VALUES (?,?,?,?)");
                    rss.setString(1,email);
                    rss.setString(2,name);
                    rss.setString(3,last_name);
                    rss.setString(4,password);
                    resultado=rss.executeUpdate();
                }
                db.close();
        }
        catch (Exception e)
        {
            System.out.println("Error: "+e);
        }
        if(resultado==1){
            HttpSession sesion=request.getSession();
            sesion.setAttribute("userName",name);
            sesion.setAttribute("userEmail",email);
            response.sendRedirect("ShowInfo");
        }
        PrintWriter out = response.getWriter();
        out.println(array);
        out.close();
    }
}
