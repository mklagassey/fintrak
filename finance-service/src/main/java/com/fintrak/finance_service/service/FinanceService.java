package com.fintrak.finance_service.service;

import com.fintrak.finance_service.dto.TransactionRequest;
import com.fintrak.finance_service.dto.TransactionResponse;
import com.fintrak.finance_service.model.Budget;
import com.fintrak.finance_service.model.Transaction;
import com.fintrak.finance_service.repository.BudgetRepository;
import com.fintrak.finance_service.repository.TransactionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FinanceService {

    private final TransactionRepository transactionRepository;
    private final BudgetRepository budgetRepository;

    @Transactional(readOnly = true)
    public List<TransactionResponse> getTransactionsByUserId(String userId) {
        return transactionRepository.findByUserIdOrderByDateDesc(userId)
                .stream()
                .map(TransactionResponse::fromEntity)
                .collect(Collectors.toList());
    }

    @Transactional
    public TransactionResponse createTransaction(TransactionRequest request, String userId) {
        // 1. Create and save the transaction
        Transaction transaction = new Transaction();
        transaction.setUserId(userId);
        transaction.setAmount(request.getAmount());
        transaction.setType(request.getType());
        transaction.setCategory(request.getCategory());
        transaction.setDescription(request.getDescription());
        transaction.setDate(request.getDate());

        Transaction savedTransaction = transactionRepository.save(transaction);

        // 2. If it's an expense, find and update the corresponding budget
        if (transaction.getType() == Transaction.TransactionType.EXPENSE) {
            updateBudgetForExpense(transaction);
        }

        // 3. Return the response DTO
        return TransactionResponse.fromEntity(savedTransaction);
    }

    private void updateBudgetForExpense(Transaction transaction) {
        // Find the active budget for this transaction's category and date
        Optional<Budget> activeBudgetOpt = budgetRepository.findByUserIdAndCategoryAndStartDateLessThanEqualAndEndDateGreaterThanEqual(
                transaction.getUserId(),
                transaction.getCategory(),
                transaction.getDate(),
                transaction.getDate()
        );

        // If a budget exists, add the transaction amount to its current spending
        activeBudgetOpt.ifPresent(budget -> {
            budget.setCurrentAmount(budget.getCurrentAmount().add(transaction.getAmount()));
            budgetRepository.save(budget);
        });
    }
}
