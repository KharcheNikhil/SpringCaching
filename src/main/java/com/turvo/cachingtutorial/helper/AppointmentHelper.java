package com.turvo.cachingtutorial.helper;

import com.turvo.cachingtutorial.model.Appointment;
import com.turvo.cachingtutorial.persistence.AppointmentRepository;
import java.util.List;
import java.util.UUID;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class AppointmentHelper {

  @Autowired
  private AppointmentRepository repository;

  @Autowired
  private CacheHelper cacheHelper;

  public List<Appointment> findAll() {
    return repository.findAll();
  }

  public Appointment findByAppointmentId(UUID appointmentId) {
    return cacheHelper.getAppointmentFromCache(appointmentId);
  }

  public Appointment doNotWork(UUID appointmentId) {
    return doNotWorkCached(appointmentId);
  }

  @Cacheable(key = "NOT_CACHED", cacheNames = "not_working_cache")
  private Appointment doNotWorkCached(UUID appointmentId) {
    log.info("Implememntation gets called {}, appointmentId: {}", "doNotWork", appointmentId);
    return null;
  }

}
