package bell.commonmodel.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * Общий ресурс. Отображение данных о погоде
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class WeatherView implements Serializable {
    private String city;
    private String country;
    private double windSpeed;
    private String condition;
    private int temperature;
}