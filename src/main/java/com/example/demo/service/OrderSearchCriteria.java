package com.example.demo.service;

import com.example.demo.entity.Address;
import lombok.Data;
import java.time.LocalDateTime;

@Data
public class OrderSearchCriteria {
    private Address address;         // Адрес доставки
    private LocalDateTime startDate; // Начало временного интервала
    private LocalDateTime endDate;   // Конец временного интервала
    private String paymentType;      // Тип оплаты: "CASH", "CHECK" или "CREDIT"
    private String customerName;     // Имя пользователя
    private String orderStatus;      // Статус заказа
}