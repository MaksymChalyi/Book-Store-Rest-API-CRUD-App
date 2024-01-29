package com.maksimkaxxl.Entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AuthorEntity {

    private Integer id;
    private String firstName;
    private String lastName;
    private String phone;
    private String email;
}

// CREATE TABLE authour
//(
//    id         INTEGER PRIMARY KEY NOT NULL,
//    first_name VARCHAR(255)        NOT NULL,
//    last_name  VARCHAR(255)        NOT NULL,
//    phone      VARCHAR(20),
//    email      VARCHAR(128)
//
//);
