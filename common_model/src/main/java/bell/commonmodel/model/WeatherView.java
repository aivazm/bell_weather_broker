package bell.commonmodel.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class WeatherView implements Serializable {
    private static final long serialVersionUID = 1L;
    private String city;
    private String country;
    private double windSpeed;
    private String condition;
    private int temperature;
//
//    public WeatherView() {
//    }
//
//    public String getCity() {
//        return city;
//    }
//
//    public void setCity(String city) {
//        this.city = city;
//    }
//
//    public String getCountry() {
//        return country;
//    }
//
//    public void setCountry(String country) {
//        this.country = country;
//    }
//
//    public double getWindSpeed() {
//        return windSpeed;
//    }
//
//    public void setWindSpeed(double windSpeed) {
//        this.windSpeed = windSpeed;
//    }
//
//    public String getCondition() {
//        return condition;
//    }
//
//    public void setCondition(String condition) {
//        this.condition = condition;
//    }
//
//    public int getTemperature() {
//        return temperature;
//    }
//
//    public void setTemperature(int temperature) {
//        this.temperature = temperature;
//    }
//
//    @Override
//    public String toString() {
//        return "WeatherView{" +
//                "city='" + city + '\'' +
//                ", country='" + country + '\'' +
//                ", windSpeed=" + windSpeed +
//                ", condition='" + condition + '\'' +
//                ", temperature=" + temperature +
//                '}';
//    }
}