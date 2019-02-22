package com.bell.weatherbroker.repository;

import com.bell.weatherbroker.model.Weather;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import java.util.List;
import java.util.logging.Logger;

@RequestScoped
@Transactional
public class WeatherRepositoryImpl implements WeatherRepository {
    private static Logger log = Logger.getLogger(WeatherRepositoryImpl.class.getName());

    @PersistenceContext(unitName="weatherPU")
    private EntityManager em;

    @Override
    public void add(Weather model) {
        em.persist(model);
        log.info("Запись о погоде в городе "+ model.getCityName() + " добавлена");
    }

    @Override
    public Weather findByCityName(String cityName) {
        Weather weather;

        TypedQuery<Weather> query;
        List<Weather> list;
        query = em.createQuery("select w from Weather w where w.cityName = :city", Weather.class);
        query.setParameter("city", cityName);
        list = query.getResultList();

        if (list.size()==1){
            weather = list.get(0);
        } else if (list.size()>1){
            list.sort((o1, o2) -> (int) (o2.getId()-o1.getId()));
            weather = list.get(0);
        } else {
            return null;
        }
        return weather;
    }
}
