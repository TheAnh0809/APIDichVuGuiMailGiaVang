package com.example.apidichvuguimailgiavang.controller;

import com.example.apidichvuguimailgiavang.exeption.ResponeAPI;
import com.example.apidichvuguimailgiavang.model.User;
import com.example.apidichvuguimailgiavang.repository.UserResponsitory;
import com.example.apidichvuguimailgiavang.service.DichVuMailGiaVang;
import com.example.apidichvuguimailgiavang.service.ThirdService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedHashMap;

@RestController
@RequestMapping(path = "/gold", produces = "application/json")
@CrossOrigin(origins = "*")
public class ServiceController {
    DichVuMailGiaVang dichVuMailGiaVang;
    ThirdService goldService;
    @Autowired
    public ServiceController(UserResponsitory userResponsitory){
        dichVuMailGiaVang = new DichVuMailGiaVang(userResponsitory);
        goldService = new ThirdService();
    }
    @PostMapping("/dangkythongtin")
    public ResponeAPI dangKyThongTin(@RequestBody LinkedHashMap object) throws IOException, InterruptedException {
        ResponeAPI res = new ResponeAPI();
        User user = new User();
        user.setName((String) object.get("name"));
        user.setEmail((String) object.get("email"));
        user.setMavang((String) object.get("mavang"));
        user.setMatien((String) object.get("matien"));
        user.setGiamin((Double) object.get("giamin"));
        user.setGiamax((Double) object.get("giamax"));

        // Check tồn tại thông tin vàng và loại tiền tệ và giá
        String postEndpoint = "http://127.0.0.1:8080/gold/checkthongtintontai";
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        Date date = new Date();
        String inputJson = user.tojson();
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
        if(map.get("error") != null){
            res.setDataFromJson(map);
            return res;
        }
        // Check trùng thông tin đã đăng ký trước đó
        postEndpoint = "http://127.0.0.1:8080/gold/checktrungthongtin";
        formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        date = new Date();
        inputJson = user.tojson();
        request = HttpRequest.newBuilder()
                .uri(URI.create(postEndpoint))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(inputJson))
                .build();
        client = HttpClient.newHttpClient();
        response = client.send(request, HttpResponse.BodyHandlers.ofString());
        data = response.body();
        mapper = new ObjectMapper();
        map = mapper.readValue(data, LinkedHashMap.class);
        if(map.get("error") != null){
            res.setDataFromJson(map);
            return res;
        }
        // Lưu thông tin đăng ký sau khi đã check
        postEndpoint = "http://127.0.0.1:8080/gold/luudangki";
        formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        date = new Date();
        inputJson = user.tojson();
        request = HttpRequest.newBuilder()
                .uri(URI.create(postEndpoint))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(inputJson))
                .build();
        client = HttpClient.newHttpClient();
        response = client.send(request, HttpResponse.BodyHandlers.ofString());
        data = response.body();
        mapper = new ObjectMapper();
        map = mapper.readValue(data, LinkedHashMap.class);
        res.setDataFromJson(map);
        return res;
    }

    @PostMapping("/checktrungthongtin")
    public ResponeAPI checkTrungThongTin(@RequestBody LinkedHashMap object){
        ResponeAPI res = new ResponeAPI();
        User user = new User();
        user.setName((String) object.get("name"));
        user.setEmail((String) object.get("email"));
        user.setMavang((String) object.get("mavang"));
        user.setMatien((String) object.get("matien"));
        user.setGiamin((Double) object.get("giamin"));
        user.setGiamax((Double) object.get("giamax"));
        res = dichVuMailGiaVang.checkTrungThongTin(user);
        return res;
    }
    @PostMapping("/checkthongtintontai")
    public ResponeAPI checkTTtontai(@RequestBody LinkedHashMap object){
        ResponeAPI res = new ResponeAPI();
        User user = new User();
        user.setName((String) object.get("name"));
        user.setEmail((String) object.get("email"));
        user.setMavang((String) object.get("mavang"));
        user.setMatien((String) object.get("matien"));
        user.setGiamin((Double) object.get("giamin"));
        user.setGiamax((Double) object.get("giamax"));
        res = dichVuMailGiaVang.checkDieuKienNhanThongBao(user);
        return res;
    }
    @PostMapping("/luudangki")
    public ResponeAPI luuDangKy(@RequestBody LinkedHashMap object){
        ResponeAPI res = new ResponeAPI();
        User user = new User();
        user.setName((String) object.get("name"));
        user.setEmail((String) object.get("email"));
        user.setMavang((String) object.get("mavang"));
        user.setMatien((String) object.get("matien"));
        user.setGiamin((Double) object.get("giamin"));
        user.setGiamax((Double) object.get("giamax"));
        res = dichVuMailGiaVang.luuThongTinDangKy(user);
        return res;
    }
    @PostMapping("/guimail")
    public void guiMailBienDong(){
        dichVuMailGiaVang.guiMailBienDong();
    }
    @GetMapping("/gold/{loai}/{tien}")
    public ResponeAPI getGold(@PathVariable String loai, @PathVariable String tien) throws IOException, InterruptedException {
        ResponeAPI res = new ResponeAPI();
        res.setData(goldService.getGold(loai, tien));
        return res;
    }
    @PostMapping("/dudoan")
    public ResponeAPI duDoanGiaVang() throws IOException, InterruptedException {
        ResponeAPI res = new ResponeAPI();
        dichVuMailGiaVang.guimailDuDoan();
        return res;
    }
}
