package com.example.searchplace.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class ParameterDto {

    @NotBlank
    @Max(50)
    private String query;

}
