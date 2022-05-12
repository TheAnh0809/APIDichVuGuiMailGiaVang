package com.example.apidichvuguimailgiavang.service;

import com.example.apidichvuguimailgiavang.exeption.ResponeAPI;
import com.example.apidichvuguimailgiavang.model.InfoNotification;
import com.example.apidichvuguimailgiavang.model.User;
import com.example.apidichvuguimailgiavang.repository.InfoNotificationRespository;
import com.example.apidichvuguimailgiavang.repository.UserResponsitory;
import org.springframework.beans.factory.annotation.Autowired;

public class UserService {
    private UserResponsitory userRepo;
    private InfoNotificationRespository infoRepo;

    @Autowired
    public UserService(UserResponsitory gRepo, InfoNotificationRespository infoRepo) {
        this.userRepo = gRepo;
        this.infoRepo = infoRepo;
    }
    public ResponeAPI xacThucRegisterEmail(User user, InfoNotification infor) {
        ResponeAPI responeAPI = new ResponeAPI();
        user = userRepo.findUsersByEmail(user.getEmail());
        if(checkMaXacThuc(user)) {
            if(checkTrungThongTinDangKy(infor)){
                EmailService emailService = new EmailService(user.getEmail(),"Đăng ký thành công", "Bạn đã đăng ký thông báo biến động số dư thành công!!!\n" +
                        "Chúng tôi sẽ gửi thông báo cho bạn khi vàng "+ infor.getLoai()+" tại "+ infor.getKhuvuc() +" đạt mốc giá bán là "+infor.getGiaban()+"" +
                        " hoặc giá mua đạt mốc "+ infor.getGiamua()+"\nCảm ơn đã sử dụng dịch vụ, chúc bạn thành công trong cuộc sống!!!");
                responeAPI.setData("Đăng ký thành công với email : "+user.getEmail());
                if (emailService.send()){;
                    infoRepo.save(infor);
                    return responeAPI;
                }else {
                    responeAPI.setData("Đăng ký thất bại!!! Có lỗi trong quá trình xác nhận email của bạn!!!");
                    return responeAPI;
                }
            } else {
                responeAPI.setData("Đăng ký thất bại!!! Bạn đã đăng ký thông tin này rồi!!!");
                return responeAPI;
            }
        } else {
            responeAPI.setData("Đăng ký thất bại!!! Mã xác thực sai!!!");
            return responeAPI;
        }
    }
    public ResponeAPI xacThucEmailMuonXoa(User user){
        ResponeAPI responeAPI = new ResponeAPI();
        user = userRepo.findUsersByEmail(user.getEmail());
        if(checkTrungEmail(user)){
            EmailService emailService = new EmailService(user.getEmail(), "Mã xác thực huỷ đăng ký thông báo biến động vàng",
                    user.getMaxacnhan());
            if(emailService.send()){
                responeAPI.setData("Hệ thống đã gửi mã xác nhận đến email : "+ user.getEmail());
                return responeAPI;
            } else {
                responeAPI.setData("Gửi xác nhận thất bại!!! Có lỗi trong quá trình xác nhận email của bạn!!!");
                return responeAPI;
            }
        } else {
            responeAPI.setData("Gửi xác nhận thất bại!!! Bạn chưa từng đăng ký dịch vụ của chúng tôi!!!");
            return responeAPI;
        }
    }
    private   boolean checkMaXacThuc(User user){
        User datacheck = userRepo.findUserByEmailAndMaxacnhan(user.getEmail(), user.getMaxacnhan());
        if(datacheck == null) return false;
        return  true;
    }
    private   boolean checkTrungThongTinDangKy(InfoNotification infoNotification){
        InfoNotification info = infoRepo.findInfoNotificationByEmailUserAndLoaiAndKhuvucAndGiabanAndGiamua(
                infoNotification.getEmailUser(), infoNotification.getLoai(), infoNotification.getKhuvuc(), infoNotification.getGiaban(), infoNotification.getGiamua());
        if (info == null) return true;
        return  false;
    }
    private boolean checkTrungEmail(User user){
        User datacheck = userRepo.findUsersByEmail(user.getEmail());
        if(datacheck == null) return false;
        return  true;
    }
}
