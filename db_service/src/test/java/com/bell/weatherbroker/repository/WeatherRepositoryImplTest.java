package com.bell.weatherbroker.repository;

import com.bell.weatherbroker.model.Weather;
import org.junit.Before;
import org.junit.Test;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class WeatherRepositoryImplTest {

    private final EntityManager em = mock(EntityManager.class);
    private static final String CITY = "cityName";
    private static final String QUERY_STRING = "select w from Weather w where w.cityName = :city";
    private TypedQuery query = mock(TypedQuery.class);

    private Weather model;
    private WeatherRepositoryImpl repository;

    @Before
    public void setUp() {
        model = Weather.builder()
                .cityName("city")
                .countryName("country")
                .condition("condition")
                .windSpeed(1.0)
                .temperature(10)
                .build();

        repository = new WeatherRepositoryImpl();
        repository.setEm(em);
        when(em.createQuery(QUERY_STRING, Weather.class)).thenReturn(query);
    }

    @Test
    public void add() {
        repository.add(model);
        verify(em).persist(model);
    }

    @Test
    public void findByCityName() {
        repository.findByCityName(CITY);
        verify(em).createQuery(QUERY_STRING, Weather.class);
        verify(query).setParameter("city", CITY);
        verify(query).getResultList();

    }
}