package com.fintrak.finance_service.controller;

import com.fintrak.finance_service.dto.TransactionRequest;
import com.fintrak.finance_service.dto.TransactionResponse;
import com.fintrak.finance_service.service.FinanceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/finance")
@RequiredArgsConstructor
public class FinanceController {

    private final FinanceService financeService;

    /**
     * NOTE: In a real microservices architecture, the user ID would typically be extracted
     * from the JWT token by the API Gateway and passed downstream as a request header.
     */
    private static final String USER_ID_HEADER = "X-User-Id";

    @PostMapping("/transactions")
    public ResponseEntity<TransactionResponse> createTransaction(
            @RequestHeader(USER_ID_HEADER) String userId,
            @RequestBody TransactionRequest transactionRequest) {
        TransactionResponse createdTransaction = financeService.createTransaction(transactionRequest, userId);
        return new ResponseEntity<>(createdTransaction, HttpStatus.CREATED);
    }

    @GetMapping("/transactions")
    public ResponseEntity<List<TransactionResponse>> getTransactions(
            @RequestHeader(USER_ID_HEADER) String userId) {
        List<TransactionResponse> transactions = financeService.getTransactionsByUserId(userId);
        return ResponseEntity.ok(transactions);
    }
}
