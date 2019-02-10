package com.ddd.books.in.spring.func.books.poll;

import com.ddd.books.in.spring.func.books.wishlists.BookWish;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookInPoll {
    @Id
    private UUID id;

    @NotNull
    private BookWish book;

    @NotNull
    @Positive
    private Integer occurrenceInWishlists;

    @NotNull
    @Positive
    private Integer votes;
}
