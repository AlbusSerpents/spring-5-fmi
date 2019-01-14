package com.ddd.books.in.spring.func.clubs;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
public class ClubInfo {

    private UUID id;

    private String name;

    private String topic;

    private String description;
}
