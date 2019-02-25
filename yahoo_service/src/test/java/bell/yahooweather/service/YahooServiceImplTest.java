package bell.yahooweather.service;

import bell.commonmodel.model.WeatherView;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import javax.crypto.Mac;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class YahooServiceImplTest {

    private static final String CITY = "cityName";
    private static final String APP_ID = "appId";
    private final HttpClient client = mock(HttpClient.class);
    private final HttpGet request = mock(HttpGet.class);
    private HttpResponse response = mock(HttpResponse.class);

    private YahooServiceImpl service;
    private WeatherView view;

    @Before
    public void setUp() throws IOException {
        service = new YahooServiceImpl();
        view = WeatherView.builder()
                .city("city")
                .country("country")
                .condition("condition")
                .windSpeed(1.0)
                .temperature(10)
                .build();
        when(client.execute(request)).thenReturn(response);
    }

    @Test
    public void getWeatherFromYahoo() throws IOException {
        service.getWeatherFromYahoo(CITY);
        Assert.assertNotNull(view);
    }

    @Test(expected = NoSuchAlgorithmException.class)
    public void getWeatherFromYahooNoSuchAlgorithmException() throws Exception {
        when(Mac.getInstance(APP_ID)).thenThrow(Exception.class);
        service.getWeatherFromYahoo(CITY);
    }

    @Test
    public void getWeatherFromYahooReturnNull() throws IOException {
        response = null;
        when(client.execute(request)).thenReturn(response);
        service.getWeatherFromYahoo(CITY);
        Assert.assertNull(response);
    }

}