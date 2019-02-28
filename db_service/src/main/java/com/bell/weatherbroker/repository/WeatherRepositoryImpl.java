package com.bell.weatherbroker.repository;

import com.bell.weatherbroker.model.Weather;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import javax.enterprise.context.RequestScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import java.util.List;

/**
 * {@inheritDoc}
 */
@Slf4j
@RequestScoped
@Transactional
public class WeatherRepositoryImpl implements WeatherRepository {

    private static final String QUERY_STRING = "select w from Weather w where w.cityName = :city";

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
        log.info("Data on the weather in the city " + weather.getCityName() + " added");
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
        TypedQuery<Weather> query;
        List<Weather> list;
        query = em.createQuery(QUERY_STRING, Weather.class);
        query.setParameter("city", cityName);
        list = query.getResultList();

        if (list.size() == 1) {
            return list.get(0);
        } else if (list.size() > 1) {
            list.sort((o1, o2) -> (int) (o2.getId() - o1.getId()));
            return list.get(0);
        }
        return null;

    }
}
