package com.finance.dashboard.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;

@Data
@AllArgsConstructor 
@NoArgsConstructor  
public class TrendDTO {
    private String period; 
    private BigDecimal amount;
    private String type; 
    
    public TrendDTO(String period, BigDecimal amount, String type) {
        this.period = period;
        this.amount = amount;
        this.type = type;
    }
}