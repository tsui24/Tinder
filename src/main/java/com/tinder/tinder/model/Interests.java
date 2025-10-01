package com.tinder.tinder.model;

import jakarta.persistence.*;

@Table
@Entity(name = "interests")
public class Interests {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
}
