package com.finance.dashboard.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.finance.dashboard.Repository.FinancialRecordRepository;
import com.finance.dashboard.entities.FinancialRecord;
import com.finance.dashboard.entities.TransactionType;

@Service
public class FinancialRecordService {

    @Autowired
    private FinancialRecordRepository recordRepository;

    
    // 1. CREATE or UPDATE a record
    public FinancialRecord saveOrUpdate(FinancialRecord record) {
        // Simple save: Spring JPA handles both insert and update
        return recordRepository.save(record);
    }

   
    // 2. GET ALL records (Only those not soft-deleted)
    public List<FinancialRecord> getAllActiveRecords() {
        // Using the custom query we wrote in the Repository
        return recordRepository.findAllByDeletedFalse();
    }

    
    // 3. FILTER records (The Advanced Search)
    public Page<FinancialRecord> getFilteredRecords(
            TransactionType type, 
            String category, 
            LocalDate from, 
            LocalDate to, 
            Pageable pageable) {
        
        // This calls the @Query we wrote with the ":type IS NULL" logic
        return recordRepository.findWithFilters(type, category, from, to, pageable);
    }


     // 4. SOFT DELETE a record
    public void softDelete(Long id) {
        // Step A: Find the record first
        Optional<FinancialRecord> optionalRecord = recordRepository.findById(id);

        if (optionalRecord.isPresent()) {
            // Step B: Get the object from the Optional
            FinancialRecord record = optionalRecord.get();
            
            // Step C: Change the 'deleted' flag to true
            record.setDeleted(true);
            
            // Step D: Save the change back to database
            recordRepository.save(record);
        } else {
            // If ID doesn't exist, we throw an error so the user knows
            throw new RuntimeException("Financial Record not found with ID: " + id);
        }
    }

    // 5. GET ONE record by ID
    public FinancialRecord getById(Long id) {
        Optional<FinancialRecord> record = recordRepository.findById(id);
        
        if (record.isPresent()) {
            return record.get();
        } else {
            return null; 
        }
    }
}