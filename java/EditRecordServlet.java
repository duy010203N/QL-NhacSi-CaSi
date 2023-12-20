import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
@WebServlet("/EditRecordServlet")
public class EditRecordServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;
        Connection con;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        
        out.println("<html>");
        out.println("<head>");
        out.println("<title>Form Sửa Dữ Liệu Nhạc Sĩ</title>"); 
        out.println("</head>");
        
        out.println("<body>");
        
        String MNS = request.getParameter("MANHACSI");
        
        out.println("<h2>Form Sửa Dữ Liệu Nhạc Sĩ</h2>");
        out.println("<form action='EditRecordServlet' method='post'>");
       
        out.println("  <input type='hidden' name='MANHACSI' value='" + MNS+ "'/>");
        
        out.println("  <label for='TenNhacSi'>Tên nhạc sĩ:</label>");
        out.println("  <input type='text' id='TenNhacSi' name='TenNhacSi' required><br>");

        out.println("  <label for='DiaChi'>Địa Chỉ:</label>"); 
        out.println("  <input type='text' id='DiaChi' name='DiaChi' required><br>");

        out.println("  <label for='SoDienThoai'>Số điện thoại:</label>");
        out.println("  <input type='text' id='SoDienThoai' name='SoDienThoai' required><br><br>");  

        out.println("  <input type='submit' value='Lưu'>");
        out.println("</form>");
        
        out.println("</body>");
        out.println("</html>");

    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    response.setContentType("text/html");

    // Lấy dữ liệu từ form
     String MNS = request.getParameter("MANHACSI");
    String TNS = request.getParameter("TenNhacSi");
    String DC = request.getParameter("DiaChi");
    String SDT = request.getParameter("SoDienThoai");
    try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            response.getWriter().println("Lỗi khi tải driver MySQL: " + e.getMessage());
            return;
        }
    // Kết nối đến cơ sở dữ liệu
        String jdbcUrl = "jdbc:mysql://localhost:3306/duc";
        String username = "root";
        String password = "12345678";
        try (Connection connection = DriverManager.getConnection(jdbcUrl, username, password)) {

    // Câu lệnh SQL update
    String sql = "UPDATE NHACSI SET TenNhacSi = ?, DiaChi = ?, SoDienThoai = ? " + 
                "WHERE MANHACSI = ?";
    
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
    
    statement.setString(1, TNS);
    statement.setString(2, DC); 
    statement.setString(3, SDT);
    statement.setString(4, MNS);

    int rowsUpdated = statement.executeUpdate();

    if(rowsUpdated > 0) {
        PrintWriter out = response.getWriter();
        out.println("Cập nhật thành công!");
    } else {
        PrintWriter out = response.getWriter();
        out.println("Không tìm thấy nhạc sĩ!");
    }
            }
} catch (Exception e) {
    e.printStackTrace();
    response.getWriter().println(e.getMessage()); 
}
    }
}
