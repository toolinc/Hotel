package services;

import org.apache.commons.lang3.StringEscapeUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class HotelInfoServlet extends HttpServlet{

    /**
     * A method that gets executed when the get request is sent to the
     * HelloServlet
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html");
        response.setStatus(HttpServletResponse.SC_OK);

        PrintWriter out = response.getWriter();
        String name = request.getParameter("name");
        if (name == null || name.isEmpty())
            name = "anonymous";

        System.out.println(name);
        name = StringEscapeUtils.escapeHtml4(name); // need to "clean up" whatever
        // the user entered
        System.out.println(name);
        // writing to the response
        out.println("<html>");
        out.println("<body>");
        out.println("<h1>Hello, " + name + "!</h1>");
        out.println("</body>");
        out.println("</html>");
    }
}
