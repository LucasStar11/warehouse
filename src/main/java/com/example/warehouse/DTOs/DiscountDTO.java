package com.example.warehouse.DTOs;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class DiscountDTO {
    @NotNull
    @NotEmpty
    private String name;
    @Min(1)
    @Max(100)
    @NotNull
    private Integer discount;
}
