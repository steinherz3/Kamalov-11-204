package org.example.model;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Tournament {

    Long id;

    String name;

    String description;

    LocalDateTime startTime;
}
