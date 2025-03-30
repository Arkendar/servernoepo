package com.example.demo.other;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class CreateOrderRequest {
    @Data
    public static class OrderDetailDto {
        @NotNull
        private Integer quantity;
        private String taxStatus;
    }
    @NotNull
    private LocalDateTime date;

    @NotBlank
    private String status;

    @NotNull
    private Long customerId;

    @Valid
    @NotEmpty
    private List<OrderDetailDto> details;

    @Valid
    @NotNull
    private PaymentDto payment;
    
    @Data
    @JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "type")
    @JsonSubTypes({
        @JsonSubTypes.Type(value = CashDto.class, name = "CASH"),
        @JsonSubTypes.Type(value = CheckDto.class, name = "CHECK"),
        @JsonSubTypes.Type(value = CreditDto.class, name = "CREDIT")
    })
    public abstract static class PaymentDto {
        private float amount;
        public abstract String getType();
    }
    
    @Data
    @EqualsAndHashCode(callSuper = true)
    public static class CashDto extends PaymentDto {
        private float cashTendered;
        @Override public String getType() { return "CASH"; }
    }
    
    @Data
    @EqualsAndHashCode(callSuper = true)
    public static class CheckDto extends PaymentDto {
        private String name;
        private String bankID;
        @Override public String getType() { return "CHECK"; }
    }

    @Data
    @EqualsAndHashCode(callSuper = true)
    public static class CreditDto extends PaymentDto {
        private String number;
        private String cardType; // Переименовано
        private LocalDateTime expDate;
        @Override public String getType() { return "CREDIT"; }
    }
}