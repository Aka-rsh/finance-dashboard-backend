package com.finance.dashboard.Repository;


import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable; // Correct Import
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.finance.dashboard.entities.FinancialRecord;
import com.finance.dashboard.entities.TransactionType;

public interface FinancialRecordRepository extends JpaRepository<FinancialRecord, Long> {

    @Query("SELECT r FROM FinancialRecord r WHERE r.deleted = false " +
           "AND (:type IS NULL OR r.type = :type) " +
           "AND (:category IS NULL OR r.category = :category) " +
           "AND (:from IS NULL OR r.date >= :from) " +
           "AND (:to IS NULL OR r.date <= :to)")
    Page<FinancialRecord> findWithFilters(
        @Param("type") TransactionType type,
        @Param("category") String category,
        @Param("from") LocalDate from,
        @Param("to") LocalDate to, 
        Pageable pageable // Use Spring Data Pageable
    );

    // Dashboard: Summing totals
    @Query("SELECT COALESCE(SUM(r.amount), 0) FROM FinancialRecord r " +
           "WHERE r.deleted = false AND r.type = :type")
    BigDecimal sumByType(@Param("type") TransactionType type);

    // Dashboard: Category-wise breakdown
    @Query("SELECT r.category, SUM(r.amount) FROM FinancialRecord r " +
           "WHERE r.deleted = false GROUP BY r.category")
    List<Object[]> getCategoryTotals();

    // Dashboard: Monthly Trends (Total Income/Expense per month)
    @Query("SELECT FUNCTION('MONTH', r.date) as month, FUNCTION('YEAR', r.date) as year, " +
           "r.type, SUM(r.amount) FROM FinancialRecord r " +
           "WHERE r.deleted = false GROUP BY year, month, r.type ORDER BY year, month")
    List<Object[]> getMonthlyTrends();

	List<FinancialRecord> findAllByDeletedFalse();
}

