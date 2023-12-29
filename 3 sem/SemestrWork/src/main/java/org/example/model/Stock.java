package org.example.model;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Stock {

    Long id;

    String name;

    Long count;

    Long price;
}
