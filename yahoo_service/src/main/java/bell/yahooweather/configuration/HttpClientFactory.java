package bell.yahooweather.configuration;


import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;

import javax.enterprise.inject.Produces;
import javax.inject.Named;


public class HttpClientFactory {

    private PoolingHttpClientConnectionManager connectionManager() {
        PoolingHttpClientConnectionManager connectionManager = new PoolingHttpClientConnectionManager();
        connectionManager.setMaxTotal(10);
        return connectionManager;
    }

    @Produces
    @Named(value = "client")
    public HttpClient httpClient() {
        return HttpClients.custom()
                .setConnectionManager(connectionManager())
                .build();
    }
}
