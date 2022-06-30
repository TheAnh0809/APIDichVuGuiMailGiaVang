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

    @Column(name = "mavang", nullable = false)
    private String mavang;
    @Column(name = "matien", nullable = false)
    private String matien;
    @Column(name = "giamin", nullable = false)
    private double giamin;
    @Column(name = "giamax", nullable = false)
    private double giamax;
    public User() {

    }
    public String tojson(){
        return  "{\n" +
                "    \"name\" : \""+this.name+"\",\n" +
                "    \"email\":\""+this.email+"\",\n" +
                "    \"mavang\":\""+this.mavang+"\",\n" +
                "    \"matien\":\""+this.matien+"\",\n" +
                "    \"giamin\" :"+this.giamin+",\n" +
                "    \"giamax\":"+this.giamax+"\n" +
                "}";
    }
}
