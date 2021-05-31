import com.mysql.jdbc.Connection;
import com.mysql.jdbc.Statement;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.simple.JSONArray;

public class Login extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException
    {
        response.setContentType("application/json;charset=UTF-8");
        String email=request.getParameter("Email");
        String password= request.getParameter("Password");
        JSONArray array=new JSONArray();

        boolean respuesta;
        try
        {
            Class.forName("com.mysql.jdbc.Driver");
            Connection db = (Connection) DriverManager.getConnection("jdbc:mysql://localhost:3306/Usuarios","root", "1234");
            Statement stmt = (Statement) db.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM Usuario where email = '" + email + "'");
            respuesta=rs.next();
            String passwrd=rs.getString("password");
            if(respuesta && passwrd.equals(password)){
                Map objeto=new HashMap();
                    objeto.put("id",rs.getInt("idUsuario"));
                    objeto.put("nombre",rs.getString("nombre"));
                    objeto.put("paterno",rs.getString("paterno"));
                    objeto.put("materno",rs.getString("materno"));
                    objeto.put("password",rs.getString("password"));
                    array.add(objeto);
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
