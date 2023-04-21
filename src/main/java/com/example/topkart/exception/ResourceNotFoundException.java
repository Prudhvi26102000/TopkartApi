package com.example.topkart.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class ResourceNotFoundException extends RuntimeException{

    private static final long serialVersionUID = 1L;

    String resourceName;
    String fieldName;
    int fieldValue;
    long value;

    public ResourceNotFoundException(String resourceName, String fieldName, Integer id) {
        super(String.format("%s not found with %s :%s",resourceName,fieldName,id));
        this.resourceName = resourceName;
        this.fieldName = fieldName;
        this.fieldValue = id;
    }

    public ResourceNotFoundException(String resourceName, String fieldName, long id) {
        super(String.format("%s not found with %s :%s",resourceName,fieldName,id));
        this.resourceName = resourceName;
        this.fieldName = fieldName;
        this.value = id;
    }

}