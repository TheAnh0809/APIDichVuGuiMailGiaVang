package com.example.apidichvuguimailgiavang.model;

import lombok.Data;

import java.util.LinkedHashMap;

@Data
public class Gold {
    private String loai;
    private String khuVuc;
    private String giaMua;
    private String giaBan;
    private String ngay;

    public Gold toGold(LinkedHashMap<String, Object> data){
        this.loai = (String) data.get("loai");
        this.khuVuc = (String) data.get("khuvuc");
        this.giaBan = (String) data.get("giaBan");
        this.giaMua = (String) data.get("giaMua");
        this.ngay = (String) data.get("ngay");
        return this;
    }
}
