import java.io.*;
import javax.servlet.http.*;
public class UserInterface extends HttpServlet {
  /**
     * 
     */
    private static final long serialVersionUID = 1L;
    public UserInterface(){
    }
    public void doGet(HttpServletRequest request, HttpServletResponse response)
    throws IOException {
        String searchQuery = request.getParameter("searchQuery");
        StringBuilder contentBuilder = new StringBuilder();
        try {
            BufferedReader in = new BufferedReader(new FileReader("05-19-00-31-56-78.html"));
            String str;
            while ((str = in.readLine()) != null) {
                contentBuilder.append(str);
            }
            in.close();
        } catch (IOException e) {
        }
        String content = contentBuilder.toString();
        response.setContentType("text/html");
        response.getWriter().println(content);
        
        String page = "<!doctype html> <html> <body> <h1>" + searchQuery +" </h1> </body></html>";
        response.getWriter().println(page);
    }  
}