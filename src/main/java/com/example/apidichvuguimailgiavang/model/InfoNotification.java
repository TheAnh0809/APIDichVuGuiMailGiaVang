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
@Table(name = "tbl_infornotification", schema = "gold")
public class InfoNotification {
    @Id
    @Column(name = "id", nullable = false)
    private int id;
    @Column(name = "emailuser", nullable = false)
    private String emailUser;
    @Column(name = "loai", nullable = false)
    private String loai;
    @Column(name = "khuvuc", nullable = false)
    private String khuvuc;
    @Column(name = "giaban", nullable = false)
    private String giaban;
    @Column(name = "giamua", nullable = false)
    private String giamua;
    public InfoNotification() {

    }
}
