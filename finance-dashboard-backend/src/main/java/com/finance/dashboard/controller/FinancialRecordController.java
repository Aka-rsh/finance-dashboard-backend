package com.finance.dashboard.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import com.finance.dashboard.entities.FinancialRecord;
import com.finance.dashboard.entities.User;
import com.finance.dashboard.service.FinancialRecordService;
import com.finance.dashboard.service.UserService;
import com.finance.dashboard.dto.response.*;
import com.finance.dashboard.dto.response.ResponseStructureDto;

import java.util.List;

@RestController
@RequestMapping("/api/records")
public class FinancialRecordController {

    @Autowired
    private FinancialRecordService recordService;

    @Autowired
    private UserService userService;
    // 1. CREATE a Record
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/add")
    public ResponseEntity<ResponseStructureDto<FinancialRecord>> add(@RequestBody FinancialRecord req) {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = auth.getName();
        User user = userService.getUserByEmail(email);

        FinancialRecord record = new FinancialRecord();
        record.setAmount(req.getAmount());
        record.setType(req.getType());
        record.setCategory(req.getCategory());
        record.setDate(req.getDate());
        record.setNotes(req.getNotes());
        record.setCreatedBy(user);

        FinancialRecord saved = recordService.saveOrUpdate(record);

        ResponseStructureDto<FinancialRecord> response = new ResponseStructureDto<>();
        response.setStatusCode(HttpStatus.CREATED.value());
        response.setMessage("Financial record saved");
        response.setData(saved);

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
    // 2. GET ALL ACTIVE RECORDS
    @GetMapping("/active")
    public ResponseEntity<ResponseStructureDto<List<FinancialRecord>>> getActive() {
        List<FinancialRecord> records = recordService.getAllActiveRecords();

        ResponseStructureDto<List<FinancialRecord>> response = new ResponseStructureDto<>();
        response.setStatusCode(HttpStatus.OK.value());
        response.setMessage("Active records retrieved");
        response.setData(records);

        return ResponseEntity.ok(response);
    }

 // 3. DELETE (Soft Delete)
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ResponseStructureDto<String>> delete(@PathVariable Long id) {
        recordService.softDelete(id);

        ResponseStructureDto<String> response = new ResponseStructureDto<>();
        response.setStatusCode(HttpStatus.OK.value());
        response.setMessage("Record soft-deleted successfully");
        response.setData("Deleted ID: " + id);

        return ResponseEntity.ok(response);
    }
}