package com.example.apidichvuguimailgiavang.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Data
@AllArgsConstructor
@Table(name = "tbl_user", schema = "gold")
public class User {
    @Id
    @Column(name = "id", nullable = false)
    private int id;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "maxacnhan", nullable = false)
    private String maxacnhan;


    public User() {

    }
}
