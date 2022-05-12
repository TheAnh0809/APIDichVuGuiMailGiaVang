package com.example.apidichvuguimailgiavang.service;

import com.example.apidichvuguimailgiavang.exeption.ResponeAPI;
import com.example.apidichvuguimailgiavang.model.Gold;
import com.example.apidichvuguimailgiavang.model.InfoNotification;
import com.example.apidichvuguimailgiavang.model.User;
import com.example.apidichvuguimailgiavang.repository.InfoNotificationRespository;
import com.example.apidichvuguimailgiavang.repository.UserResponsitory;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class NotificationService {
    private UserResponsitory userRepo;
    private InfoNotificationRespository infoRepo;

    @Autowired
    public NotificationService(UserResponsitory gRepo, InfoNotificationRespository infoRepo) {
        this.userRepo = gRepo;
        this.infoRepo = infoRepo;
    }


    public  ResponeAPI registerEmail(User user){
        ResponeAPI responeAPI = new ResponeAPI();
        if(checkTrungEmail(user)){
            user = userRepo.findUsersByEmail(user.getEmail());
        } else {
            Random random = new Random();
            int ma = random.nextInt(99999 + 1 - 10000) + 10000;
            user.setMaxacnhan(Integer.toString(ma));
            userRepo.save(user);
        }
        EmailService emailService = new EmailService(user.getEmail(), "Mã xác thực yêu cầu đăng ký thông báo biến động vàng",
                user.getMaxacnhan());
        if(emailService.send()){
            responeAPI.setData("Hệ thống đã gửi mã xác nhận đến email : "+ user.getEmail());
            return responeAPI;
        } else {
            responeAPI.setData("Đăng ký thất bại!!! Có lỗi trong quá trình xác nhận email của bạn!!!");
            return responeAPI;
        }
    }

    private boolean checkTrungEmail(User user){
        User datacheck = userRepo.findUsersByEmail(user.getEmail());
        if(datacheck == null) return false;
        return  true;
    }

    private   boolean checkMaXacThuc(User user){
        User datacheck = userRepo.findUserByEmailAndMaxacnhan(user.getEmail(), user.getMaxacnhan());
        if(datacheck == null) return false;
        return  true;
    }


    public  ResponeAPI xoaDichVuEmail(User user){
        ResponeAPI responeAPI = new ResponeAPI();
        if (checkMaXacThuc(user)){
            User data = userRepo.findUsersByEmail(user.getEmail());
            userRepo.delete(data);
            List<InfoNotification> listdata = infoRepo.findInfoNotificationsByEmailUser(user.getEmail());
            listdata.forEach(obj -> {
                infoRepo.delete(obj);
            });
            responeAPI.setData("Đã huỷ đang ký thông báo biến động email : "+user.getEmail());
            return responeAPI;
        } else {
            responeAPI.setData("Mã xác nhận sai!!!");
            return responeAPI;
        }
    }
    private boolean soSanhLonHon(String a, String b){
        long ax = Long.parseLong(a);
        long bx = Long.parseLong(b);
        if(ax >= bx) return  true;
        else  return false;
    }
    private boolean soSanhNhoHon(String a, String b){
        long ax = Long.parseLong(a);
        long bx = Long.parseLong(b);
        if(ax <= bx) return  true;
        else  return false;
    }
    public void dichVuGuiMailThongBao(InfoNotification infoNotification) throws IOException, InterruptedException {
        GoldService goldService = new GoldService();
        Gold gold = goldService.getGold(infoNotification);
        if( soSanhLonHon(gold.getGiaBan(), infoNotification.getGiaban())) {
            EmailService emailService = new EmailService(infoNotification.getEmailUser(),
                    "Thông báo giá vàng chạm mốc",
                    ""+ infoNotification.getLoai() +" tại " + infoNotification.getKhuvuc() + " đã chạm mốc " + infoNotification.getGiaban());
            emailService.send();
        }
        if( soSanhNhoHon(gold.getGiaMua(), infoNotification.getGiamua())){
            EmailService emailService = new EmailService(infoNotification.getEmailUser(),
                    "Thông báo giá vàng chạm mốc",
                    ""+ infoNotification.getLoai() +" tại " + infoNotification.getKhuvuc() + " đã chạm mốc " + infoNotification.getGiamua());
            emailService.send();
        }
    }
    public void docEmailVaGuiThongBao(){
        ArrayList<InfoNotification> listData = new ArrayList<>();
        listData = (ArrayList<InfoNotification>) infoRepo.findAll();
        listData.forEach(data ->{
            try {
                this.dichVuGuiMailThongBao(data);
            } catch (IOException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
    }
}
