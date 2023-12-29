package org.example.model;

import lombok.*;
import org.example.model.enums.BookingStatus;

import java.time.Instant;
import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Booking {

    Long id;

    Long userId;

    Long pcId;

    LocalDateTime timeFrom;

    LocalDateTime timeTo;

    BookingStatus status;
}
