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

public class ShowInfo extends HttpServlet {
        @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException
    {
        response.setContentType("application/json;charset=UTF-8");
        HttpSession session=request.getSession();
        String id=(String)session.getAttribute("id");
        JSONArray array=new JSONArray();
        boolean respuesta;
        if(id!=null)
            try
            {

            Class.forName("com.mysql.jdbc.Driver");
            Connection db = (Connection) DriverManager.getConnection("jdbc:mysql://localhost:3306/Usuarios","root", "1234");
            Statement stmt = (Statement) db.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM Usuario where idUsuario = '" + id + "'");
            respuesta=rs.next();
            if(respuesta){
                Map objeto=new HashMap();
                objeto.put("id",id);
                objeto.put("ID",rs.getString("ID"));
                array.add(objeto);
                rs = stmt.executeQuery("select * from Ejercicios where idUsuario="+rs.getInt("idUsuario"));
                JSONArray array2=new JSONArray();
                while (rs.next()){
                        Map objeto2=new HashMap();
                        objeto2.put("idEjercicio",rs.getString("idEjercicio"));
                        objeto2.put("JSON",rs.getString("JSON"));
                        array2.add(objeto2);
                }
                array.add(array2);
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
