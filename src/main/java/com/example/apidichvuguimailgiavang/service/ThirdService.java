package com.example.apidichvuguimailgiavang.service;

import com.example.apidichvuguimailgiavang.exeption.ResponeAPI;
import com.example.apidichvuguimailgiavang.model.Gold;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.net.httpserver.Headers;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.LinkedHashMap;

public class ThirdService {
    public ThirdService() {
    }
    public double getTiGiaDongTien(String tien) throws IOException, InterruptedException {
        if(tien.equals("USD")) return 1.0;
        String postEndpoint = "https://api.apilayer.com/fixer/convert?to="+tien+"&from=USD&amount=1";
        String inputJson = "";
        var request = HttpRequest.newBuilder()
                .uri(URI.create(postEndpoint))
                .headers("apikey", "EWNLL3VFhkYsrsI7M1XS6iCYLtheg5C8")
                .GET()
                .build();
        var client = HttpClient.newHttpClient();
        var response = client.send(request, HttpResponse.BodyHandlers.ofString());
        String data = response.body();
        ObjectMapper mapper = new ObjectMapper();
        LinkedHashMap<String, Object> map = mapper.readValue(data, LinkedHashMap.class);
        double result = (double) map.get("result");
        return  result;
    }
    public double getPriceOil() throws IOException, InterruptedException {
        String postEndpoint = "https://api.oilpriceapi.com/v1/prices/latest";
        String inputJson = "";
        var request = HttpRequest.newBuilder()
                .uri(URI.create(postEndpoint))
                .headers("Authorization", "Token 6ae564bb833d69dcf4f0a472163f0716").headers("Content-Type", "application/json")
                .GET()
                .build();
        var client = HttpClient.newHttpClient();
        var response = client.send(request, HttpResponse.BodyHandlers.ofString());
        String data = response.body();
        ObjectMapper mapper = new ObjectMapper();
        LinkedHashMap<String, Object> map = mapper.readValue(data, LinkedHashMap.class);

        LinkedHashMap<String, Object> map1 = (LinkedHashMap<String, Object>) map.get("data");
        double result = (double) map1.get("price");
        return  result;
    }
    public Gold getGold(String loai, String loaitien) throws IOException, InterruptedException {
        String postEndpoint = "https://www.goldapi.io/api/"+loai+"/"+loaitien;
        var myHeaders = new Headers();
        myHeaders.add("x-access-token", "goldapi-3st3z618l2y5vlrl-io");
        myHeaders.add("Content-Type", "application/json");

        String inputJson = "";
        var request = HttpRequest.newBuilder()
                .uri(URI.create(postEndpoint))
                .headers("x-access-token", "goldapi-3st3z618l2y5vlrl-io").headers("Content-Type", "application/json")
                .GET()
                .build();
        var client = HttpClient.newHttpClient();
        var response = client.send(request, HttpResponse.BodyHandlers.ofString());
        String data = response.body();
        ObjectMapper mapper = new ObjectMapper();
        LinkedHashMap<String, Object> map = mapper.readValue(data, LinkedHashMap.class);
        Gold g =  new Gold();
        g.toGold(map);
        return  g;
    }
}
