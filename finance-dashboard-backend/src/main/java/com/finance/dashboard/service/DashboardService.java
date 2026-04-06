package com.finance.dashboard.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.finance.dashboard.Repository.FinancialRecordRepository;
import com.finance.dashboard.dto.response.*;
import com.finance.dashboard.entities.FinancialRecord;
import com.finance.dashboard.entities.TransactionType;

@Service
public class DashboardService {

    @Autowired
    private FinancialRecordRepository recordRepository;

    public ResponseEntity<ResponseStructureDto<DashboardSummaryDTO>> getDashboardSummary() {
        
        // 1. Fetch Totals
        BigDecimal totalIncome = recordRepository.sumByType(TransactionType.INCOME);
        BigDecimal totalExpenses = recordRepository.sumByType(TransactionType.EXPENSE);
        
        if (totalIncome == null) totalIncome = BigDecimal.ZERO;
        if (totalExpenses == null) totalExpenses = BigDecimal.ZERO;
        
        BigDecimal netBalance = totalIncome.subtract(totalExpenses);

        // 2. Map Category Totals (FIXED: Added arguments to constructor)
        List<CategorySummaryDTO> categoryTotals = recordRepository.getCategoryTotals()
                .stream()
                .map(obj -> new CategorySummaryDTO((String) obj[0], (BigDecimal) obj[1]))
                .collect(Collectors.toList());

        // 3. Recent Activity
        List<FinancialRecord> recentActivity = recordRepository.findAll(
                PageRequest.of(0, 5, Sort.by(Sort.Direction.DESC, "date"))
        ).getContent();

        // 4. Map Trends
        List<TrendDTO> monthlyTrends = recordRepository.getMonthlyTrends()
                .stream()
                .map(obj -> new TrendDTO(
                        obj[1].toString() + "-" + obj[0].toString(), 
                        (BigDecimal) obj[3], 
                        obj[2].toString()
                ))
                .collect(Collectors.toList());

        // 5. Build DTO (FIXED: Using the Constructor with data)
        DashboardSummaryDTO summary = new DashboardSummaryDTO(
            totalIncome, 
            totalExpenses, 
            netBalance, 
            categoryTotals, 
            recentActivity, 
            monthlyTrends
        );

        // 6. Wrap in ResponseStructureDto (FIXED: Removed duplicate 'response' variable)
        ResponseStructureDto<DashboardSummaryDTO> response = new ResponseStructureDto<>();
        response.setStatusCode(HttpStatus.OK.value());
        response.setMessage("Dashboard statistics retrieved successfully");
        response.setData(summary);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}