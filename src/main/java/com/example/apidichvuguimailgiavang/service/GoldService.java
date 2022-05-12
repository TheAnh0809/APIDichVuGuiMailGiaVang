package com.example.apidichvuguimailgiavang.service;

import com.example.apidichvuguimailgiavang.model.Gold;
import com.example.apidichvuguimailgiavang.model.InfoNotification;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.*;
import java.net.*;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.text.SimpleDateFormat;
import java.util.*;

public class GoldService {
    public GoldService() {
    }

    public Gold getGold(InfoNotification info) throws IOException, InterruptedException {
        Gold gold = new Gold();
        String postEndpoint = "http://127.0.0.1:8081/gold/lkvn";
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        Date date = new Date();
        String inputJson = "{\n" +
                "    \"loai\": \""+ info.getLoai() +"\",\n" +
                "    \"khuvuc\": \""+info.getKhuvuc()+"\",\n" +
                "    \"ngay\": \""+"11/05/2022"+"\"\n" +
                "}";;

        var request = HttpRequest.newBuilder()
                .uri(URI.create(postEndpoint))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(inputJson))
                .build();

        var client = HttpClient.newHttpClient();

        var response = client.send(request, HttpResponse.BodyHandlers.ofString());
        String data = response.body();
        ObjectMapper mapper = new ObjectMapper();
        LinkedHashMap<String, Object> map = mapper.readValue(data, LinkedHashMap.class);
        gold.toGold(map);
        return gold;
    }
}
