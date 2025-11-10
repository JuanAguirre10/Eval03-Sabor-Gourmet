package com.tecsup.saborgourmet;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@SpringBootApplication
@EnableAspectJAutoProxy
public class SaborGourmetApplication {

    public static void main(String[] args) {
        SpringApplication.run(SaborGourmetApplication.class, args);
        System.out.println("\n========================================");
        System.out.println("🍽️  SABOR GOURMET - SISTEMA INICIADO");
        System.out.println("========================================");
        System.out.println("📍 URL: http://localhost:8080/saborgourmet");
        System.out.println("👤 Usuario Admin: admin / admin123");
        System.out.println("========================================\n");
    }
}