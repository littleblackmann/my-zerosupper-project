package com.zerosupper.order;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public record OrderResponse(Long orderId, String userEmail, BigDecimal totalAmount,
                            LocalDate arrivalDate, LocalTime arrivalTime, String phoneNumber,
                            OrderStatus status, Instant createdAt, List<ItemResponse> items) {
    public static OrderResponse from(CustomerOrder order) {
        return new OrderResponse(order.getId(), order.getUser().getEmail(), order.getTotalAmount(),
                order.getArrivalDate(), order.getArrivalTime(), order.getPhoneNumber(), order.getStatus(),
                order.getCreatedAt(), order.getItems().stream().map(ItemResponse::from).toList());
    }

    public record ItemResponse(Long productId, String productName, BigDecimal unitPrice,
                               int quantity, BigDecimal lineTotal) {
        static ItemResponse from(OrderItem item) {
            return new ItemResponse(item.getProductId(), item.getProductName(), item.getUnitPrice(),
                    item.getQuantity(), item.getLineTotal());
        }
    }
}
