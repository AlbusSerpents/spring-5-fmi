package com.ddd.books.in.spring.func.books.poll;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Poll {

    @Id
    private UUID id;

    @NotNull
    List<BookInPoll> booksInPoll;

    @NotNull
    private Date endDate;

    @NotNull
    private List<UUID> voters;
}
