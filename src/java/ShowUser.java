import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.json.simple.JSONArray;

public class ShowUser extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException
    {
        HttpSession session=request.getSession();
        String userSelected=request.getParameter("userSelected");
        String userName=(String)session.getAttribute("userName");
        String userEmail=(String)session.getAttribute("userEmail");
        response.setContentType("application/json;charset=UTF-8");
        JSONArray array=new JSONArray();
        boolean resultado=false;
        if(userName!=null&&userEmail!=null)
            try{
                Class.forName("com.mysql.jdbc.Driver");
                Connection db = DriverManager.getConnection("jdbc:mysql://localhost:3306/Usuarios","root", "1234");
                Statement stmt = db.createStatement();
                ResultSet rs = stmt.executeQuery("select * from Usuario where idUsuario="+userSelected+"");
                resultado=rs.next();
                if (resultado)
                {
                    Map objeto=new HashMap();
                        objeto.put("id",rs.getString("idUsuario"));
                        objeto.put("email",rs.getString("email"));
                        objeto.put("nombre",rs.getString("nombre"));
                        objeto.put("apellido",rs.getString("apellido"));
                        objeto.put("password",rs.getString("password"));
                        array.add(objeto);
                }
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
