package bell.yahooweather.service;

import bell.commonmodel.model.WeatherView;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.junit.Before;
import org.junit.Test;

import javax.crypto.Mac;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class YahooServiceImplTest {

    private static final String CITY = "cityName";
    private final HttpClient client = mock(HttpClient.class);
    private final HttpGet request = mock(HttpGet.class);
    private final HttpResponse response = mock(HttpResponse.class);
    private final ObjectMapper mapper = mock(ObjectMapper.class);

    private YahooServiceImpl service;
    private WeatherView view;

    @Before
    public void setUp() throws IOException {
        service = new YahooServiceImpl(client, mapper);
        view = WeatherView.builder()
                .city("city")
                .country("country")
                .condition("condition")
                .windSpeed(1.0)
                .temperature(10)
                .build();

        when(client.execute(request)).thenReturn(response);
    }

//    @Test
//    public void getWeatherFromYahoo() {
//        service.getWeatherFromYahoo(CITY);
//        Assert.assertNotNull(view);
//    }

    @Test(expected = RuntimeException.class)
    public void getWeatherFromYahooException() throws NoSuchAlgorithmException {
        when(Mac.getInstance("HmacSHA1")).thenThrow(Exception.class);
        service.getWeatherFromYahoo(CITY);
    }

    @Test(expected = RuntimeException.class)
    public void getWeatherFromYahooEmptyParameterReturnNull() {
        service.getWeatherFromYahoo(null);
    }

}