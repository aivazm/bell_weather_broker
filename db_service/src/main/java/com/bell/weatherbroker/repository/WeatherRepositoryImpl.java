package com.bell.weatherbroker.repository;

import com.bell.weatherbroker.model.Weather;
import lombok.extern.slf4j.Slf4j;

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
        if (weather != null) {
            em.persist(weather);
            log.info("Data on the weather in the city " + weather.getCityName() + " added");
            return weather;
        } else {
            log.info("Data on the weather has not been added");
            throw new RuntimeException("Parameter Weather is null");
        }

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Weather findByCityName(String cityName) {
        Weather weather = null;
        if (cityName == null || cityName.equals("")) {
            log.info("Empty parameter cityName");
        } else{
            TypedQuery<Weather> query;
            List<Weather> list;
            query = em.createQuery("select w from Weather w where w.cityName = :city", Weather.class);
            query.setParameter("city", cityName);
            list = query.getResultList();

            if (list.size() == 1) {
                weather = list.get(0);
            } else if (list.size() > 1) {
                list.sort((o1, o2) -> (int) (o2.getId() - o1.getId()));
                weather = list.get(0);
            }
        }
        return weather;
    }
}
