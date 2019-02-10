package com.ddd.books.in.spring.func.boxes;

import com.ddd.books.in.spring.func.books.Book;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.util.UUID;
import static java.util.UUID.randomUUID;

@Data
@Document
@NoArgsConstructor
@AllArgsConstructor

public class Box {

    @Id
    final private UUID id = randomUUID();
    private String location;

}
