package com.zerosupper.product;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.math.BigDecimal;

public record ProductInput(
        @NotBlank @Size(max = 120) String productName,
        @NotNull ProductCategory category,
        @NotNull @DecimalMin(value = "0.01") @Digits(integer = 10, fraction = 2) BigDecimal price,
        @Min(0) @Max(100000) int stock,
        @Size(max = 2000) String description,
        @Size(max = 2048) String imageUrl) {
}
