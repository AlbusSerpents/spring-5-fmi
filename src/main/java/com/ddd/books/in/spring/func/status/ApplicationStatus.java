package com.ddd.books.in.spring.func.status;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ApplicationStatus {
    private final String applicationName;
    private final String version;
    private final String applicationStatus;
}
