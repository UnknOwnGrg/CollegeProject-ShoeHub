package com.CollegeProject.Ecomm.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

@Entity
public class CartDb {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    private UserDtls user;

    @ManyToOne
    private Product product;

    private int quantity;

    @Transient //it will not create in db
    private Double totalPrice;
}
