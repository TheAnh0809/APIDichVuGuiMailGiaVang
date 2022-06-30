package com.example.apidichvuguimailgiavang.exeption;

import org.springframework.http.HttpStatus;

import java.util.LinkedHashMap;

public class ResponeAPI {

    private Integer status;
    private Object data;
    private Object error;

    public ResponeAPI() {
        this.status = HttpStatus.OK.value();
        this.data = data;
        this.error = error;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public Object getError() {
        return error;
    }

    public void setError(Object error) {
        this.error = error;
    }
    public void setDataFromJson(LinkedHashMap<String, Object> map){
        this.setData(map.get("data"));
        this.setError(map.get("error"));
    }
    @Override
    public String toString() {
        return "ResponeAPI{" +
                "status=" + status +
                ", data=" + data +
                ", error=" + error +
                '}';
    }
}