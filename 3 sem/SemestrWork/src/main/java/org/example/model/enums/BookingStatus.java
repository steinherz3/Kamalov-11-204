package org.example.model.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum BookingStatus {
    WAITING("waiting"),
    ACCEPTED("accepted"),
    DENIED("denied");

    private final String value;
}
