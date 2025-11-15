package com.FourSolutions.library.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = "books")
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "author")
    private String author;

    @Column(name = "title")
    private String title;

    @Column(name = "availability")
    private boolean availability;

    @Column(name = "borrowed_by")
    private String borrowedBy;

    @Column(name = "date")
    @JsonFormat(pattern = "dd.MM.yyyy")
    private LocalDate date;
}
