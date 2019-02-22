import bell.adminapi.Sender;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.logging.Logger;


public class ProducerJmsServlet extends javax.servlet.http.HttpServlet {
    private static Logger log = Logger.getLogger(ProducerJmsServlet.class.getName());

    @Inject
    private Sender sender;


    @Override
    protected void service(HttpServletRequest request, HttpServletResponse resp) throws ServletException, IOException {
        String value = request.getParameter("cityName");
        if (value.equals("")) {
            log.info("Пустое поле cityName");
        } else {
            sender.sendMessage(value);
        }
        resp.sendRedirect("index.jsp");


    }
}
