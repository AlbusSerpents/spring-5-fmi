package com.ddd.books.in.spring.func.books.poll;

import com.ddd.books.in.spring.func.books.wishlists.BookWish;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PollResults {

    @NotNull
    List<BookWish> winners;

    @NotNull
    List<BookWish> losers;
}
