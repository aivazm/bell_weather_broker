package bell.yahooweather.service;

import bell.commonmodel.model.WeatherView;
import bell.yahooweather.dto.YahooWeather;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import javax.enterprise.context.RequestScoped;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Date;
import java.util.List;
import java.util.Random;

/**
 * {@inheritDoc}
 */
@Slf4j
@RequestScoped
public class YahooServiceImpl implements YahooService {

    private static final String APP_ID = "jh65Mv72";
    private static final String CONSUMER_KEY = "dj0yJmk9Y3pZNEVYdTdTMDJWJnM9Y29uc3VtZXJzZWNyZXQmc3Y9MCZ4PTQ1";
    private static final String CONSUMER_SECRET = "5b00e5641f0cb68a9d05369d5ad40236c1d53aa7";
    private static final String URL = "https://weather-ydn-yql.media.yahoo.com/forecastrss";


    private static HttpClient client = HttpClientBuilder.create().build();
    private static ObjectMapper mapper = new ObjectMapper();

    YahooServiceImpl() {
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public WeatherView getWeatherFromYahoo(String cityName) {
        if (StringUtils.isBlank(cityName)) {
            throw new RuntimeException("Parameter cityName is null or empty");
        }

        long timestamp = new Date().getTime() / 1000;
        byte[] nonce = new byte[32];
        Random rand = new Random();
        rand.nextBytes(nonce);
        String oauthNonce = new String(nonce).replaceAll("\\W", "");

        String parameters = getParameters(cityName, oauthNonce, timestamp);
        String signature = getSignature(parameters, oauthNonce, timestamp);
        YahooWeather yahooWeather = getYahooWeatherData(cityName, signature);

        return WeatherView
                .builder()
                .city(yahooWeather.getLocation().getCity().toLowerCase())
                .country(yahooWeather.getLocation().getCountry().toLowerCase())
                .windSpeed(yahooWeather.getCurrentObservation().getWind().getSpeed())
                .condition(yahooWeather.getCurrentObservation().getCondition().getText().toLowerCase())
                .temperature(yahooWeather.getCurrentObservation().getCondition().getTemperature())
                .build();
    }

    private String getParameters(String cityName, String oauthNonce, long timestamp) {
        List<String> parameters = new ArrayList<>();
        parameters.add("format=json");
        try {
            parameters.add("location=" + URLEncoder.encode(cityName, "UTF-8"));
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("Error while trying to add parameter location : ", e);
        }
        parameters.add("oauth_consumer_key=" + CONSUMER_KEY);
        parameters.add("oauth_nonce=" + oauthNonce);
        parameters.add("oauth_signature_method=HMAC-SHA1");
        parameters.add("oauth_timestamp=" + timestamp);
        parameters.add("oauth_version=1.0");
        parameters.add("u=c");

        StringBuilder parametersList = new StringBuilder();
        for (int i = 0; i < parameters.size(); i++) {
            parametersList.append((i > 0) ? "&" : "").append(parameters.get(i));
        }
        return parametersList.toString();
    }

    private String getSignature(String parameters, String oauthNonce, long timestamp) {
        String signatureString;
        try {
            signatureString = "GET&" +
                    URLEncoder.encode(URL, "UTF-8") + "&" +
                    URLEncoder.encode(parameters, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("Error while trying to encode parameters : ", e);
        }
        String signature;
        try {
            SecretKeySpec signingKey = new SecretKeySpec((CONSUMER_SECRET + "&").getBytes(), "HmacSHA1");
            Mac mac = Mac.getInstance("HmacSHA1");
            mac.init(signingKey);
            byte[] rawHMAC = mac.doFinal(signatureString.getBytes());
            Base64.Encoder encoder = Base64.getEncoder();
            signature = encoder.encodeToString(rawHMAC);
        } catch (Exception e) {
            throw new RuntimeException("Unable to append signature: ", e);
        }
        return "OAuth " +
                "oauth_consumer_key=\"" + CONSUMER_KEY + "\", " +
                "oauth_nonce=\"" + oauthNonce + "\", " +
                "oauth_timestamp=\"" + timestamp + "\", " +
                "oauth_signature_method=\"HMAC-SHA1\", " +
                "oauth_signature=\"" + signature + "\", " +
                "oauth_version=\"1.0\"";
    }

    private YahooWeather getYahooWeatherData(String cityName, String authorizationLine) {
        HttpGet request = new HttpGet(URL + "?location=" + cityName + "&format=json&u=c");
        request.addHeader("Authorization", authorizationLine);
        request.addHeader("Yahoo-App-Id", APP_ID);
        request.addHeader("Content-Type", "application/json");
        HttpResponse response;
        try {
            response = client.execute(request);
        } catch (IOException e) {
            throw new RuntimeException("Error while trying to get a HttpResponse: ", e);
        }
        YahooWeather yahooWeather;
        HttpEntity entity = response.getEntity();
        try {
            yahooWeather = mapper.readValue(entity.getContent(), YahooWeather.class);
        } catch (IOException e) {
            throw new RuntimeException("Error while trying to get YahooWeather-object : ", e);
        }
        if (yahooWeather.getLocation().getCity() == null) {
            throw new RuntimeException("City not found");
        }

        return yahooWeather;
    }
}
