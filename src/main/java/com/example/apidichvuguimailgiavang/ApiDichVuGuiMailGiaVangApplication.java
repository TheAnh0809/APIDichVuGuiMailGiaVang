package com.example.apidichvuguimailgiavang;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedHashMap;

@SpringBootApplication
public class ApiDichVuGuiMailGiaVangApplication {

    public static void main(String[] args) {
        SpringApplication.run(ApiDichVuGuiMailGiaVangApplication.class, args);
    }
    @Scheduled(fixedRate = 5000)
    @Scheduled(cron = "0 15 7 * * *")
    void sendNotification() throws InterruptedException, IOException {
        Thread.sleep(10000L);
        System.out.println("_________");
        // Gửi mail thông báo chạm mốc
        String postEndpoint = "http://127.0.0.1:8080/gold/guimail";
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        Date date = new Date();
        String inputJson = "";
        var request = HttpRequest.newBuilder()
                .uri(URI.create(postEndpoint))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(inputJson))
                .build();
        var client = HttpClient.newHttpClient();
        var response = client.send(request, HttpResponse.BodyHandlers.ofString());
    // Gửi mail dự đoán
         postEndpoint = "http://127.0.0.1:8080/gold/dudoan";
         formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
         date = new Date();
         inputJson = "";
         request = HttpRequest.newBuilder()
                .uri(URI.create(postEndpoint))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(inputJson))
                .build();
         client = HttpClient.newHttpClient();
         response = client.send(request, HttpResponse.BodyHandlers.ofString());
    }
}

@Configuration
@EnableScheduling
@ConditionalOnProperty(name = "scheduling.enable", matchIfMissing = true)
class SchedulingConfiguration {

}
