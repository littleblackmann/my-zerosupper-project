package com.zerosupper.product;

import java.math.BigDecimal;
import java.time.Instant;

public record ProductResponse(Long productId, String productName, ProductCategory category,
                              BigDecimal price, int stock, String description, String imageUrl,
                              Instant createdAt, Instant updatedAt) {
    public static ProductResponse from(Product product) {
        return new ProductResponse(product.getId(), product.getProductName(), product.getCategory(),
                product.getPrice(), product.getStock(), product.getDescription(), product.getImageUrl(),
                product.getCreatedAt(), product.getUpdatedAt());
    }
}
