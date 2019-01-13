package com.ddd.books.in.spring.func.boxes;

import javax.validation.constraints.NotBlank;

public class CreateBoxRequest {
    @NotBlank
    private String location;

}
