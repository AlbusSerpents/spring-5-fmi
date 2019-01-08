package com.ddd.books.in.spring.func.clubs;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;
import java.util.UUID;

@Data
@Document
@NoArgsConstructor
@AllArgsConstructor
public class Club {

    @Id
    private UUID id;

    @Indexed(unique = true)
    private String name;
    private String description;

    private MemberInfo owner;

    private List<MemberInfo> members;

    @Data
    @Document
    @NoArgsConstructor
    @AllArgsConstructor
    public static class MemberInfo {
        @Id
        private UUID id;
        private String name;
    }
}
