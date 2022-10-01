import javax.servlet.ServletException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.http.*;
import java.io.IOException;


public class HelloServlet extends HttpServlet {
   protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
     String path = "/WEB-INF/view/index.html";
     ServletContext servletContext = getServletContext();
     RequestDispatcher requestDispatcher = servletContext.getRequestDispatcher(path);
     requestDispatcher.forward(req, resp);
   }
}
