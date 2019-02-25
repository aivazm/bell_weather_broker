import bell.adminapi.sender.Sender;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Сервлет. Обрабатывает запросы на добавление нового города.
 */
public class ProducerJmsServlet extends javax.servlet.http.HttpServlet {

    private final Sender sender;

    @Inject
    public ProducerJmsServlet(Sender sender) {
        this.sender = sender;
    }

    /**
     * Метод обрабатывает запросы. Извлекает cityName и передает его в sender
     *
     * @param request
     * @param resp
     * @throws IOException
     */
    @Override
    protected void service(HttpServletRequest request, HttpServletResponse resp) throws IOException {
        String value = request.getParameter("cityName");
        sender.sendMessage(value);
        resp.sendRedirect("index.jsp");
    }
}
