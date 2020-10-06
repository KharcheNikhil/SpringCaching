package com.turvo.cachingtutorial.resource;

import com.turvo.cachingtutorial.helper.AppointmentHelper;
import com.turvo.cachingtutorial.model.Appointment;
import java.util.List;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/findAppointments")
public class AppointmentController {

  @Autowired
  private AppointmentHelper appointmentHelper;

  @GetMapping
  public ResponseEntity<List<Appointment>> findAll(){
      return ResponseEntity.ok(appointmentHelper.findAll());
  }

  @GetMapping("/{id}")
  public ResponseEntity<Appointment> findOne(@PathVariable("id") UUID id){
    return ResponseEntity.ok(appointmentHelper.findByAppointmentId(id));
  }

  @GetMapping("/not_working")
  public ResponseEntity<Appointment> findOne(){
    return ResponseEntity.ok(appointmentHelper.doNotWork(null));
  }
}
