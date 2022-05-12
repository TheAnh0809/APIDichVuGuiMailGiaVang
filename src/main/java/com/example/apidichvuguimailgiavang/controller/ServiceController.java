package com.example.apidichvuguimailgiavang.controller;

import com.example.apidichvuguimailgiavang.exeption.ResponeAPI;
import com.example.apidichvuguimailgiavang.model.InfoNotification;
import com.example.apidichvuguimailgiavang.model.User;
import com.example.apidichvuguimailgiavang.repository.InfoNotificationRespository;
import com.example.apidichvuguimailgiavang.repository.UserResponsitory;
import com.example.apidichvuguimailgiavang.service.NotificationService;
import com.example.apidichvuguimailgiavang.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.LinkedHashMap;

@RestController
@RequestMapping(path = "/gold", produces = "application/json")
@CrossOrigin(origins = "*")
public class ServiceController {
    NotificationService notiService;
    UserService userService;
    @Autowired
    public ServiceController(UserResponsitory userResponsitory, InfoNotificationRespository infoRepo){
        notiService = new NotificationService(userResponsitory, infoRepo);
    }
    @PostMapping("Notification/guimailthongbao")
    public void guiMailthongbaoThongTin(){
        notiService.docEmailVaGuiThongBao();
    }

        @PostMapping("User/xacthucemaildangky")
    public ResponeAPI xacThucEmailDangKy(@RequestBody LinkedHashMap object) throws IOException, InterruptedException {
        ResponeAPI res = new ResponeAPI();
        User user = new User();
        user.setName((String) object.get("name"));
        user.setEmail((String) object.get("email"));
        res = notiService.registerEmail(user);
        return res;
    }
    @PostMapping("Notification/dangkythongtin")
    public ResponeAPI xacNhanThongTinDangKy(@RequestBody LinkedHashMap object){
        ResponeAPI res = new ResponeAPI();
        User user = new User();
        InfoNotification info = new InfoNotification();
        user.setName((String) object.get("name"));
        user.setEmail((String) object.get("email"));
        user.setMaxacnhan((String) object.get("maxacnhan"));
        info.setEmailUser(user.getEmail());
        info.setLoai((String) object.get("loai"));
        info.setKhuvuc((String) object.get("khuvuc"));
        info.setGiaban((String) object.get("giaban"));
        info.setGiamua((String) object.get("giamua"));
        res = userService.xacThucRegisterEmail(user, info);
        return res;
    }
    @PostMapping("User/xacthucemailxoadangky")
    public ResponeAPI xacThucEmailXoaDangKy(@RequestBody LinkedHashMap object){
        ResponeAPI res = new ResponeAPI();
        User user = new User();
        user.setEmail((String) object.get("email"));
        res = userService.xacThucEmailMuonXoa(user);
        return res;
    }
    @PostMapping("Notification/xoadichvucuaemail")
    public ResponeAPI xoaDangKy(@RequestBody LinkedHashMap object){
        ResponeAPI res = new ResponeAPI();
        User user = new User();
        user.setEmail((String) object.get("email"));
        user.setMaxacnhan((String) object.get("maxacnhan"));
        res = notiService.xoaDichVuEmail(user);
        return res;
    }

}
