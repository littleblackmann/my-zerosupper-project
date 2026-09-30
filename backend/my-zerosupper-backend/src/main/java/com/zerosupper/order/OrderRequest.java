package com.zerosupper.order;

import jakarta.validation.Valid;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public record OrderRequest(
        @NotEmpty @Size(max = 50) List<@Valid OrderItemRequest> items,
        @NotNull @FutureOrPresent LocalDate arrivalDate,
        @NotNull LocalTime arrivalTime,
        @NotBlank @Size(max = 30)
        @Pattern(regexp = "^[0-9+()\\-\\s]{8,30}$", message = "電話號碼格式不正確") String phoneNumber) {
}
