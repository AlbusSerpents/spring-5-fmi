package com.ddd.books.in.spring.func.clubs;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
public class CreateClubRequest {

    @NotBlank
    private String name;

    @NotBlank
    private String description;
}
