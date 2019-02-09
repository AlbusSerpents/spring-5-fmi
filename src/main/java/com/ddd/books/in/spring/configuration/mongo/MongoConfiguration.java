package com.ddd.books.in.spring.configuration.mongo;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoTemplate;

@Configuration
public class MongoConfiguration {

    private static final String DATABASE_NAME = "books-in-spring";
    private static final String CONNECTION_STRING = "mongodb://abcd:1234567890_A_B_C@ds125945.mlab.com:25945/books-in-spring";

    @Bean
    public MongoClient mongoClient() {
        final MongoClientURI uri = new MongoClientURI(CONNECTION_STRING);
        return new MongoClient(uri);
    }

    @Bean
    public MongoTemplate mongoTemplate(final @Autowired MongoClient client) {
        return new MongoTemplate(client, DATABASE_NAME);
    }
}
