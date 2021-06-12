import com.mysql.jdbc.Connection;
import com.mysql.jdbc.Statement;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.DriverManager;
import java.sql.ResultSet;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.json.simple.JSONArray;

public class Login extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException
    {
        response.setContentType("application/json;charset=UTF-8");
        String id=request.getParameter("ID");
        String password= request.getParameter("Password");
        JSONArray array=new JSONArray();

        boolean respuesta;
        try
        {
            Class.forName("com.mysql.jdbc.Driver");
            Connection db = (Connection) DriverManager.getConnection("jdbc:mysql://localhost:3306/Usuarios","root", "1234");
            Statement stmt = (Statement) db.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM Usuario where ID = '" + id + "'");
            respuesta=rs.next();
            String passwrd=rs.getString("password");
            if(respuesta && passwrd.equals(password)){
                HttpSession sesion=request.getSession();
                sesion.setAttribute("ID",id);
                response.sendRedirect("ShowInfo");
            }else
                System.out.println("Not found");
            db.close();
        }
        catch (Exception e)
        {
            System.out.println("Error: "+e);
        }
        PrintWriter out = response.getWriter();
        out.println(array);
        out.close();
    }
}
