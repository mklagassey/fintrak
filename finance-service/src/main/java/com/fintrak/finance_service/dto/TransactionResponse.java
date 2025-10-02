package com.fintrak.finance_service.dto;

import com.fintrak.finance_service.model.Transaction;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@NoArgsConstructor
public class TransactionResponse {
    private Long id;
    private String userId;
    private BigDecimal amount;
    private Transaction.TransactionType type;
    private String category;
    private String description;
    private LocalDate date;

    public static TransactionResponse fromEntity(Transaction transaction) {
        TransactionResponse dto = new TransactionResponse();
        dto.setId(transaction.getId());
        dto.setUserId(transaction.getUserId());
        dto.setAmount(transaction.getAmount());
        dto.setType(transaction.getType());
        dto.setCategory(transaction.getCategory());
        dto.setDescription(transaction.getDescription());
        dto.setDate(transaction.getDate());
        return dto;
    }
}
