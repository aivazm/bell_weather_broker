package bell.yahooweather.configuration;


import com.fasterxml.jackson.databind.ObjectMapper;

import javax.enterprise.inject.Produces;
import javax.inject.Named;


public class ObjectMapperFactory {


    @Produces
    @Named(value = "mapper")
    public ObjectMapper objectMapper() {
        return new ObjectMapper();
    }

}
