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
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException
    {
        HttpSession session=request.getSession();
        String id=(String)session.getAttribute("id");
        String json_=request.getParameter("JSON");
        JSONArray array=new JSONArray();
        response.setContentType("application/json;charset=UTF-8");
        int resultado=0;
        if(id!=null)
            try
            {
                Class.forName("com.mysql.jdbc.Driver");
                Connection db = DriverManager.getConnection("jdbc:mysql://localhost:3306/Usuarios","root", "1234");
                Statement stmt = db.createStatement();
                boolean respuesta=false;
                ResultSet rs = stmt.executeQuery("SELECT * FROM Usuario where idUsuario = " + id);
                respuesta=rs.next();
                if(respuesta){
                    PreparedStatement rss = db.prepareStatement("INSERT INTO Ejercicios (idUsuario,JSON) VALUES (?,?)");
                    rss.setString(1,id);
                    rss.setString(2,json_);
                    resultado=rss.executeUpdate();
                }
                if(resultado==1)
                    response.sendRedirect("ShowInfo");
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
