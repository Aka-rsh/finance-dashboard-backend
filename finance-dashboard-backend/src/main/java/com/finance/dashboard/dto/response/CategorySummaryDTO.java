package com.finance.dashboard.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor 
public class CategorySummaryDTO {
	// constructor
	public CategorySummaryDTO(String category, BigDecimal totalAmount) {
	    this.category = category;
	    this.totalAmount = totalAmount;
	}
	
	private String category;
    private BigDecimal totalAmount;
}