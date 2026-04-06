package com.finance.dashboard.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.finance.dashboard.dto.response.DashboardSummaryDTO;
import com.finance.dashboard.dto.response.ResponseStructureDto;
import com.finance.dashboard.service.DashboardService;

@RestController
@RequestMapping("/api/dashboard")
public class DashboardController {

    @Autowired
    private DashboardService dashboardService;

//     Requirement 3 & 4: Dashboard Summary API with Access Control
//     All roles are allowed to view the dashboard statistics.
    @PreAuthorize("hasAnyRole('ADMIN', 'ANALYST', 'VIEWER')")
    @GetMapping("/summary")
    public ResponseEntity<ResponseStructureDto<DashboardSummaryDTO>> getSummary() {
        return dashboardService.getDashboardSummary();
    }
}