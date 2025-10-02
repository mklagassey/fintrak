package com.fintrak.finance_service.dto;

import com.fintrak.finance_service.model.Transaction;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class TransactionRequest {
    private BigDecimal amount;
    private Transaction.TransactionType type;
    private String category;
    private String description;
    private LocalDate date;
}
