package com.codechallenge.entity;


import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder(toBuilder = true)
public class Customer {

    @Id
   // @GeneratedValue(strategy= GenerationType.UUID)
    private String id;
    private String name;
    private String email;
    private String phone;

}
