import bell.adminapi.sender.Sender;
import bell.adminapi.sender.SenderImpl;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class ProducerJmsServletTest {

    private Sender sender = mock(Sender.class);
    private HttpServletRequest request = mock(HttpServletRequest.class);
    private HttpServletResponse response = mock(HttpServletResponse.class);
    private final ProducerJmsServlet jmsServlet = new ProducerJmsServlet(sender);
    private static final String CITY = "city";
    private static final String PAGE = "index.jsp";

    @Before
    public void setUp() {
        when(request.getParameter("cityName")).thenReturn(CITY);
    }

    @Test
    public void service() throws IOException {
        jmsServlet.service(request,response);
        Mockito.verify(request, Mockito.times(1)).getParameter("cityName");
        Mockito.verify(sender, Mockito.times(1)).sendMessage(CITY);
        Mockito.verify(response, Mockito.times(1)).sendRedirect(PAGE);
    }
}