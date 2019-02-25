package bell.yahooweather.service;

import bell.commonmodel.model.WeatherView;
import bell.yahooweather.dto.YahooWeather;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import javax.enterprise.context.RequestScoped;
import java.io.IOException;
import java.net.URLEncoder;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Random;

@Slf4j
@RequestScoped
public class YahooServiceImpl implements YahooService {

    private static final String APP_ID = "jh65Mv72";
    private static final String CONSUMER_KEY = "dj0yJmk9Y3pZNEVYdTdTMDJWJnM9Y29uc3VtZXJzZWNyZXQmc3Y9MCZ4PTQ1";
    private static final String CONSUMER_SECRET = "5b00e5641f0cb68a9d05369d5ad40236c1d53aa7";

    public YahooServiceImpl() {
    }

    @Override
    public WeatherView getWeatherFromYahoo(String city) throws IOException {
        String url = "https://weather-ydn-yql.media.yahoo.com/forecastrss";
        long timestamp = new Date().getTime() / 1000;
        byte[] nonce = new byte[32];
        Random rand = new Random();
        rand.nextBytes(nonce);
        String oauthNonce = new String(nonce).replaceAll("\\W", "");

        List<String> parameters = new ArrayList<>();
        parameters.add("oauth_consumer_key=" + CONSUMER_KEY);
        parameters.add("oauth_nonce=" + oauthNonce);
        parameters.add("oauth_signature_method=HMAC-SHA1");
        parameters.add("oauth_timestamp=" + timestamp);
        parameters.add("oauth_version=1.0");
        parameters.add("location=" + URLEncoder.encode(city, "UTF-8"));
        parameters.add("format=json");
        parameters.add("u=c");

        Collections.sort(parameters);

        StringBuilder parametersList = new StringBuilder();
        for (int i = 0; i < parameters.size(); i++) {
            parametersList.append((i > 0) ? "&" : "").append(parameters.get(i));
        }

        String signatureString = "GET&" +
                URLEncoder.encode(url, "UTF-8") + "&" +
                URLEncoder.encode(parametersList.toString(), "UTF-8");

        String signature;

        try {
            SecretKeySpec signingKey = new SecretKeySpec((CONSUMER_SECRET + "&").getBytes(), "HmacSHA1");
            Mac mac = Mac.getInstance("HmacSHA1");
            mac.init(signingKey);
            byte[] rawHMAC = mac.doFinal(signatureString.getBytes());
            Base64.Encoder encoder = Base64.getEncoder();
            signature = encoder.encodeToString(rawHMAC);
        } catch (NoSuchAlgorithmException | InvalidKeyException e) {
            log.warn("Unable to append signature: ", e);
            throw new RuntimeException("Unable to append signature: ", e);
        }

        String authorizationLine = "OAuth " +
                "oauth_consumer_key=\"" + CONSUMER_KEY + "\", " +
                "oauth_nonce=\"" + oauthNonce + "\", " +
                "oauth_timestamp=\"" + timestamp + "\", " +
                "oauth_signature_method=\"HMAC-SHA1\", " +
                "oauth_signature=\"" + signature + "\", " +
                "oauth_version=\"1.0\"";

        HttpClient client = HttpClientBuilder.create().build();
        url = "https://weather-ydn-yql.media.yahoo.com/forecastrss?location=" + city + "&format=json&u=c";
        HttpGet request = new HttpGet(url);
        request.addHeader("Authorization", authorizationLine);
        request.addHeader("Yahoo-App-Id", APP_ID);
        request.addHeader("Content-Type", "application/json");

        HttpResponse response = client.execute(request);

        if (response == null) {
            return null;
        }
        HttpEntity entity = response.getEntity();
        ObjectMapper mapper = new ObjectMapper();
        YahooWeather yahooWeather = mapper.readValue(entity.getContent(), YahooWeather.class);

        if (yahooWeather.getLocation().getCity() == null) {
            log.info("City " + city + " not found");
            return null;
        }

        return WeatherView
                .builder()
                .city(yahooWeather.getLocation().getCity().toLowerCase())
                .country(yahooWeather.getLocation().getCountry().toLowerCase())
                .windSpeed(yahooWeather.getCurrentObservation().getWind().getSpeed())
                .condition(yahooWeather.getCurrentObservation().getCondition().getText().toLowerCase())
                .temperature(yahooWeather.getCurrentObservation().getCondition().getTemperature())
                .build();
    }
}
