package com.fintrak.finance_service.repository;

import com.fintrak.finance_service.model.Budget;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Optional;

@Repository
public interface BudgetRepository extends JpaRepository<Budget, Long> {
    /**
     * Finds an active budget for a given user, category, and date.
     * An active budget is one where the given date is between its start and end dates.
     */
    Optional<Budget> findByUserIdAndCategoryAndStartDateLessThanEqualAndEndDateGreaterThanEqual(
            String userId, String category, LocalDate date, LocalDate date2
    );
}
