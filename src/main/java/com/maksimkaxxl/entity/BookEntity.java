package com.maksimkaxxl.entity;

import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BookEntity {

    private Integer id;
    private String title;
    private Integer publication_year;
    private String genre;

    @ManyToOne
    @JoinColumn(name = "author_id", nullable = false)
    private Integer author_id;

}

//CREATE TABLE books
//(
//    id               INTEGER PRIMARY KEY NOT NULL,
//    title            VARCHAR(255)        NOT NULL,
//    publication_year INTEGER             NOT NULL,
//    genre            VARCHAR(128)        NOT NULL,
//    author_id        INTEGER REFERENCES authour (id) ON DELETE CASCADE
//);
