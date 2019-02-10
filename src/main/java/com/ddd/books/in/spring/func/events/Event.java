package com.ddd.books.in.spring.func.events;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;
import java.util.UUID;

import static java.util.UUID.randomUUID;

@Data
@Document
@NoArgsConstructor
@AllArgsConstructor
public class Event {
    @Id
    final private UUID id = randomUUID();
    private String location;
    @Indexed(unique = true)
    private String name;
    private String topic;
    private  List<String> participants;
}
