package com.ddd.books.in.spring.func.books;

import com.ddd.books.in.spring.func.books.Book.Comment;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.util.UUID;

@Data
@NoArgsConstructor
public class NewCommentRequest {
    @JsonIgnore
    private UUID id;
    @JsonIgnore
    private String name;

    @NotBlank
    private String comment;

    Comment toComment() {
        return new Comment(id, name, comment);
    }
}
