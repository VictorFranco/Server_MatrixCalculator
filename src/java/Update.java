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
        
        HttpSession session=request.getSession();
        String id=(String)session.getAttribute("id");
        String exercise=request.getParameter("exercise");
        String json_=request.getParameter("JSON");
        JSONArray array=new JSONArray();
        int resultado=0;
        if(id!=null)
            try{
                Class.forName("com.mysql.jdbc.Driver");
                Connection db = DriverManager.getConnection("jdbc:mysql://localhost:3306/Usuarios","root", "1234");
                Statement stmt = db.createStatement();

                boolean respuesta;
                ResultSet rs = stmt.executeQuery("SELECT * FROM Usuario where idUsuario = " + id);
                respuesta=rs.next();
                if(respuesta){
                    PreparedStatement rss = db.prepareStatement("UPDATE Ejercicios SET JSON=? where idEjercicio=?");
                    rss.setString(1,json_);
                    rss.setString(2,exercise);
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
            System.out.println("aaaaaaaa");
        }
        PrintWriter out = response.getWriter();
        out.println(array);
        out.close();
    }
}
