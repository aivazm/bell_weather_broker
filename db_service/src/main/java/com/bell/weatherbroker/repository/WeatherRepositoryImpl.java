package com.bell.weatherbroker.repository;

import com.bell.weatherbroker.model.Weather;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import javax.enterprise.context.RequestScoped;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;

/**
 * {@inheritDoc}
 */
@Slf4j
@RequestScoped
@Transactional
public class WeatherRepositoryImpl implements WeatherRepository {

    private static final String QUERY_STRING
            = "select w from Weather w where w.id = (select max(w.id) from w where w.cityName = :city)";

    private EntityManager em;

    @PersistenceContext(unitName = "weatherPU")
    public void setEm(EntityManager em) {
        this.em = em;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Weather add(Weather weather) {
        if (weather == null) {
            throw new RuntimeException("Parameter Weather is null");
        }
        em.persist(weather);
        log.info("Data on the weather in the city {} added", weather.getCityName());
        return weather;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Weather findByCityName(String cityName) {
        if (StringUtils.isBlank(cityName)) {
            throw new RuntimeException("Empty parameter cityName");
        }
        TypedQuery<Weather> query = em.createQuery(QUERY_STRING, Weather.class);
        query.setParameter("city", cityName);
        Weather weather;
        try {
            weather = query.getSingleResult();
        } catch (NoResultException e) {
            throw new RuntimeException("No result when trying to get weather from DB: ", e);
        }
        return weather;

    }
}
