import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.json.simple.JSONArray;

public class Delete extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException
    {
        HttpSession session=request.getSession();
        String id=(String)session.getAttribute("id");
        String exercise=request.getParameter("exercise");
        response.setContentType("application/json;charset=UTF-8");
        JSONArray array=new JSONArray();
        int resultado=0;
        if(id!=null)
            try{
                Class.forName("com.mysql.jdbc.Driver");
                Connection db = DriverManager.getConnection("jdbc:mysql://localhost:3306/Usuarios","root", "1234");
                PreparedStatement rs = db.prepareStatement("delete from Ejercicios where idEjercicio=?");
                rs.setString(1,exercise);
                resultado=rs.executeUpdate();
                db.close();
            }catch(Exception e){
                System.out.println("Error: "+e);
            }
            if(resultado==1)
                response.sendRedirect("ShowInfo");
        PrintWriter out = response.getWriter();
        out.println(array);
        out.close();
    }
}
