package com.example.apidichvuguimailgiavang.service;

import com.example.apidichvuguimailgiavang.exeption.ResponeAPI;
import com.example.apidichvuguimailgiavang.model.Gold;
import com.example.apidichvuguimailgiavang.model.Infor;
import com.example.apidichvuguimailgiavang.model.User;
import com.example.apidichvuguimailgiavang.repository.UserResponsitory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;

public class DichVuMailGiaVang {
    public DichVuMailGiaVang(UserResponsitory userRepo) {
        this.userRepo = userRepo;
        listTiGia = new ArrayList<>();
        listTiGia.add(new Infor("USD", 1.0));
        listTiGia.add(new Infor("AUD", 1.450337));
        listTiGia.add(new Infor("GBP", 0.82436));
        listTiGia.add(new Infor("EUR", 0.96091));
        listTiGia.add(new Infor("CHF", 0.95763));
        listTiGia.add(new Infor("CAD", 1.290105));
        listTiGia.add(new Infor("JPY", 136.1275));
        listTiGia.add(new Infor("KRW", 1299.120008));
        listTiGia.add(new Infor("INR", 79.055497));
        listTiGia.add(new Infor("MYR", 4.407496));
    }
    private double getTiGia(String a){
        return listTiGia.stream().filter(i -> i.getName().equals(a)).findFirst().map(Infor::getGia).orElse(1.0);
    }
    private ArrayList<Infor> listTiGia;
    private double giaDauHomtruoc = 111.52;
    private UserResponsitory userRepo;
    public ResponeAPI luuThongTinDangKy(User user){
        ResponeAPI responeAPI = new ResponeAPI();
        userRepo.save(user);
        responeAPI.setData("Lưu thôn tin đăng ký thành công");
        return responeAPI;
    }

    public boolean checkDataHaveInList(String A, ArrayList<String> list){
        for(int i =0; i< list.size(); i++) if(A.equals(list.get(i))) return true;
        return false;
    }
    public ResponeAPI checkDieuKienNhanThongBao(User user){
        ArrayList<String> listVang = new ArrayList<>();
        listVang.add("XAU");
        listVang.add("XAG");
        listVang.add("XPT");
        listVang.add("XPD");
        ArrayList<String> listTien = new ArrayList<>();
        listTien.add("USD");
        listTien.add("AUD");
        listTien.add("GBP");
        listTien.add("EUR");
        listTien.add("CHF");
        listTien.add("CAD");
        listTien.add("JPY");
        listTien.add("KRW");
        listTien.add("INR");
        listTien.add("MYR");
        ResponeAPI responeAPI = new ResponeAPI();
        if(checkDataHaveInList(user.getMavang(), listVang) == false) {
            responeAPI.setError("Lỗi không tìm thấy loại vàng này");
            return responeAPI;
        }
        if(checkDataHaveInList(user.getMatien(), listTien) == false){
            responeAPI.setError("Lỗi không tìm thấy loại tiền tệ này");
            return responeAPI;
        }
        if(user.getGiamax()<= user.getGiamin()) {
            responeAPI.setError("Lỗi giá biến động không hợp lệ");
            return responeAPI;
        }
        if(user.getGiamin() <= 0){
            responeAPI.setError("Lỗi giá biến động không hợp lệ");
            return responeAPI;
        }
        responeAPI.setData("Ok");
        return responeAPI;
    }
    public ResponeAPI checkTrungThongTin(User user){
        ResponeAPI responeAPI = new ResponeAPI();
        User datacheck = userRepo.findUserByEmailAndMavangAndMatien(user.getEmail(), user.getMavang(), user.getMatien());
        if(datacheck == null){
            responeAPI.setData("Ok");
        } else {
            if(user.getGiamin() == datacheck.getGiamin() && user.getGiamax() == datacheck.getGiamax()){
                responeAPI.setError("Thông tin này đã được đăng ký rồi");

            } else{
                responeAPI.setData("Thông tin đã được cập nhập");
                user.setId(datacheck.getId());
                userRepo.save(user);
            }
        }
        return responeAPI;
    }

    public void guiMailBienDong(){
        ArrayList<User> list = (ArrayList<User>) userRepo.findAll();
        list.forEach(i ->{
            ThirdService goldService = new ThirdService();
            try {
                Gold g = goldService.getGold(i.getMavang(), i.getMatien());
                if(g.getPrice() <= i.getGiamin()){
                    EmailService emailService = new EmailService(i.getEmail(), "Giá vàng biến động", "Thông báo giá vàng "+ i.getMavang()+" đã chạm mốc "+i.getGiamin() +" "+ i.getMatien());
                    emailService.send();
                } else {
                    if(g.getPrice() >= i.getGiamax()){
                        EmailService emailService = new EmailService(i.getEmail(), "Giá vàng biến động", "Thông báo giá vàng "+ i.getMavang()+" đã chạm mốc "+i.getGiamax() +" "+ i.getMatien());
                        emailService.send();
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
    }
    public double duDoanGiaVang(String tien) throws IOException, InterruptedException {
        ThirdService goldService = new ThirdService();
        double tigiagiadau = goldService.getPriceOil()/giaDauHomtruoc;
        double tigiatien = goldService.getTiGiaDongTien(tien)/getTiGia(tien);
        double tigialainganhang = 1.0;
        // Công thức dự đoán : dầu 2, tiền 5, lãi ngân hàng 3

        double result = ((tigiagiadau - 1)*2 + (tigiatien -1)*5 + (tigialainganhang-1)*3)/10;
        return result;
    }
    public void guimailDuDoan(){
        ArrayList<User> list = (ArrayList<User>) userRepo.findAll();
        list.forEach(i ->{
            try {
                if(duDoanGiaVang(i.getMatien())>0){
                    EmailService emailService = new EmailService(i.getEmail(), "Dự đoàn giá vàng biến động", "Thông báo giá vàng "+ i.getMavang()+" có thể tăng" );
                    emailService.send();
                } else{
                    EmailService emailService = new EmailService(i.getEmail(), "Dự đoàn giá vàng biến động", "Thông báo giá vàng "+ i.getMavang()+" có thể giảm" );
                    emailService.send();
                }
            } catch (IOException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
    }
}
