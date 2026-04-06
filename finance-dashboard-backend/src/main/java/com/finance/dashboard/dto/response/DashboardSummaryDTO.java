package com.finance.dashboard.dto.response;

import java.math.BigDecimal;
import java.util.List;
import com.finance.dashboard.entities.FinancialRecord;
import lombok.Data;

@Data
public class DashboardSummaryDTO {
    private BigDecimal totalIncome;
    private BigDecimal totalExpenses;
    private BigDecimal netBalance;
    private List<CategorySummaryDTO> categoryTotals;
    private List<FinancialRecord> recentActivity;
    private List<TrendDTO> monthlyTrends;

    // Default Constructor (Empty)
    public DashboardSummaryDTO() {}

    // Constructor
    public DashboardSummaryDTO(BigDecimal totalIncome, BigDecimal totalExpenses, BigDecimal netBalance,
                               List<CategorySummaryDTO> categoryTotals, List<FinancialRecord> recentActivity,
                               List<TrendDTO> monthlyTrends) {
        this.totalIncome = totalIncome;
        this.totalExpenses = totalExpenses;
        this.netBalance = netBalance;
        this.categoryTotals = categoryTotals;
        this.recentActivity = recentActivity;
        this.monthlyTrends = monthlyTrends;
    }

	public BigDecimal getTotalIncome() {
		return totalIncome;
	}

	public void setTotalIncome(BigDecimal totalIncome) {
		this.totalIncome = totalIncome;
	}

	public BigDecimal getTotalExpenses() {
		return totalExpenses;
	}

	public void setTotalExpenses(BigDecimal totalExpenses) {
		this.totalExpenses = totalExpenses;
	}

	public BigDecimal getNetBalance() {
		return netBalance;
	}

	public void setNetBalance(BigDecimal netBalance) {
		this.netBalance = netBalance;
	}

	public List<CategorySummaryDTO> getCategoryTotals() {
		return categoryTotals;
	}

	public void setCategoryTotals(List<CategorySummaryDTO> categoryTotals) {
		this.categoryTotals = categoryTotals;
	}

	public List<FinancialRecord> getRecentActivity() {
		return recentActivity;
	}

	public void setRecentActivity(List<FinancialRecord> recentActivity) {
		this.recentActivity = recentActivity;
	}

	public List<TrendDTO> getMonthlyTrends() {
		return monthlyTrends;
	}

	public void setMonthlyTrends(List<TrendDTO> monthlyTrends) {
		this.monthlyTrends = monthlyTrends;
	}
    
    
}