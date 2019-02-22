package com.bell.weatherbroker.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "weather")
public class Weather {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "city_name", length = 100, nullable = false)
    private String cityName;

    @Column(name = "country_name", length = 100, nullable = false)
    private String countryName;

    @Column(name = "wind_speed", nullable = false)
    private double windSpeed;

    @Column(name = "condition", length = 20, nullable = false)
    private String condition;

    @Column(name = "temperature", nullable = false)
    private int temperature;
}
