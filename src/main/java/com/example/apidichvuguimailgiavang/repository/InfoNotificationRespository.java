package com.example.apidichvuguimailgiavang.repository;

import com.example.apidichvuguimailgiavang.model.InfoNotification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface InfoNotificationRespository extends JpaRepository<InfoNotification, String> {
    List<InfoNotification> findInfoNotificationsByEmailUser(String email);
    void deleteInfoNotificationsByEmailUser(String email);
    InfoNotification findInfoNotificationByEmailUserAndLoaiAndKhuvucAndGiabanAndGiamua(
            String email, String loai, String khuvuc, String giaban, String giamua);
}
