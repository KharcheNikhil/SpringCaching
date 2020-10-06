package com.turvo.cachingtutorial.model;

import java.util.UUID;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Data
@Document(collection = "appointmentFormFieldData")
public class Appointment {

  @Id
  @Field("_id")
  private String id;

  @Field("appointment_id")
  private UUID appointmentId;

  @Field("confirmation_number")
  private String confirmationNumber;

}

