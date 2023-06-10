package com.nttdata.usercrud.entity;

import jakarta.persistence.*;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Setter @Getter @AllArgsConstructor @NoArgsConstructor
@Table(name = "user")
public class UserEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_user")
    private int id;


    @Column(name = "name_user", nullable = false, length = 50)
    @NotEmpty(message = "Name cannot be empty")
    private String name;


    @Column(name = "email_user", nullable = false, length = 50)
    @Email(message = "Email should be valid")
    private String email;


    @Column(name = "age_user", nullable = false)
    @Positive(message = "Age should be positive")
    private int age;

}
