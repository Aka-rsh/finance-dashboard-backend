package com.finance.dashboard.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data 
@AllArgsConstructor
@NoArgsConstructor
public class ResponseStructureDto<T> {
    private int statusCode;
    private String message;
    private T data;

    // Setters
    public void setStatusCode(int statusCode) { this.statusCode = statusCode; }
    public void setMessage(String message) { this.message = message; }
    public void setData(T data) { this.data = data; }

    // Getters
    public int getStatusCode() { return statusCode; }
    public String getMessage() { return message; }
    public T getData() { return data; }
}