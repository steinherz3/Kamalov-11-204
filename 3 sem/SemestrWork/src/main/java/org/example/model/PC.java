package org.example.model;

import lombok.*;

import java.time.Instant;
import java.time.LocalTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PC {

     Long id;

     LocalTime startWorkingTime;

     LocalTime endWorkingTime;

}
