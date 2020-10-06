package com.turvo.cachingtutorial.helper;

import com.turvo.cachingtutorial.model.Appointment;
import com.turvo.cachingtutorial.persistence.AppointmentRepository;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ConcurrentMap;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.concurrent.ConcurrentMapCache;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.stereotype.Service;


/**
 *
 * https://spring.io/guides/gs/caching/
 *
 */
@Service
@Slf4j
public class CacheHelper {

  @Autowired
  private CacheManager cacheManager;

  @Autowired
  private AppointmentRepository repository;

  public List<String> getKeys() {
    Collection<String> keys = cacheManager.getCacheNames();
    if (keys == null && keys.size() == 0) {
      return Collections.emptyList();
    }
    return new ArrayList<>(keys);
  }

  @SneakyThrows
  public List<String> getEntries(String cacheName){
    ConcurrentMapCache cache = (ConcurrentMapCache) cacheManager.getCache(cacheName);
    Field storeField = ConcurrentMapCache.class.
        getDeclaredField("store");

    storeField.setAccessible(true);

    ConcurrentMap<Object, Object> store = (ConcurrentMap<Object, Object>) storeField.get(cache);
    List<String> entries = new ArrayList<>();
    store.forEach((k,v) -> entries.add(k.toString()));
    return entries;
  }

  @Cacheable(key = "#appointmentId", cacheNames = "getAppointmentFromCache")
  public Appointment getAppointmentFromCache(UUID appointmentId) {
    log.info("Implememntation gets called {}, appointmentId: {}", "getAppointmentFromCache", appointmentId);
    List<Appointment> appointments = repository.findByAppointmentId(appointmentId);
    if(appointments == null || appointments.size() == 0){
      return null;
    }
    return appointments.get(0);
  }

  @Cacheable(key = "#appointmentId", cacheNames = "getAppointmentFromRedis", cacheManager = "redisCacheManager")
  public Appointment getAppointmentFromRedis(UUID appointmentId) {
    log.info("Implememntation gets called {}, appointmentId: {}", "getAppointmentFromRedis", appointmentId);
    List<Appointment> appointments = repository.findByAppointmentId(appointmentId);
    if(appointments == null || appointments.size() == 0){
      return null;
    }
    return appointments.get(0);
  }

  @CacheEvict(
      allEntries = true,
      cacheNames = {"getAppointmentFromCache"},
      condition = "")
  public void evict() {
  }
}
