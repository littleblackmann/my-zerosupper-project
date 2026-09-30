package com.zerosupper.product;

import com.zerosupper.common.ApiException;
import java.util.Locale;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ProductService {
    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Transactional(readOnly = true)
    public ProductPage list(String category, String search, String orderBy, String sortDirection,
                            int limit, int offset) {
        int safeLimit = Math.max(1, Math.min(limit, 100));
        int safeOffset = Math.max(offset, 0);
        Sort.Direction direction = "asc".equalsIgnoreCase(sortDirection) ? Sort.Direction.ASC : Sort.Direction.DESC;
        String sortProperty = switch (orderBy == null ? "" : orderBy) {
            case "name", "productName" -> "productName";
            case "price" -> "price";
            case "stock" -> "stock";
            default -> "createdAt";
        };
        var pageable = PageRequest.of(safeOffset / safeLimit, safeLimit, Sort.by(direction, sortProperty));

        Specification<Product> specification = (root, query, builder) -> builder.isTrue(root.get("active"));
        if (category != null && !category.isBlank()) {
            ProductCategory parsedCategory;
            try {
                parsedCategory = ProductCategory.valueOf(category.trim().toUpperCase(Locale.ROOT));
            } catch (IllegalArgumentException exception) {
                throw new ApiException(HttpStatus.BAD_REQUEST, "INVALID_CATEGORY", "不支援的商品類別。");
            }
            specification = specification.and((root, query, builder) ->
                    builder.equal(root.get("category"), parsedCategory));
        }
        if (search != null && !search.isBlank()) {
            String pattern = "%" + search.trim().toLowerCase(Locale.ROOT) + "%";
            specification = specification.and((root, query, builder) -> builder.or(
                    builder.like(builder.lower(root.<String>get("productName")), pattern),
                    builder.like(builder.lower(root.<String>get("description")), pattern)));
        }

        Page<Product> page = productRepository.findAll(specification, pageable);
        return new ProductPage(page.getTotalElements(), safeLimit, safeOffset,
                page.getContent().stream().map(ProductResponse::from).toList());
    }

    @Transactional
    public ProductResponse create(ProductInput input) {
        Product product = new Product(input.productName().trim(), input.category(), input.price(), input.stock(),
                clean(input.description()), clean(input.imageUrl()));
        return ProductResponse.from(productRepository.save(product));
    }

    @Transactional
    public ProductResponse update(Long id, ProductInput input) {
        Product product = productRepository.findById(id)
                .filter(Product::isActive)
                .orElseThrow(() -> notFound(id));
        product.update(input.productName().trim(), input.category(), input.price(), input.stock(),
                clean(input.description()), clean(input.imageUrl()));
        return ProductResponse.from(product);
    }

    @Transactional
    public void delete(Long id) {
        Product product = productRepository.findById(id)
                .filter(Product::isActive)
                .orElseThrow(() -> notFound(id));
        product.deactivate();
    }

    private ApiException notFound(Long id) {
        return new ApiException(HttpStatus.NOT_FOUND, "PRODUCT_NOT_FOUND", "找不到商品：" + id);
    }

    private String clean(String value) {
        return value == null || value.isBlank() ? null : value.trim();
    }

    public record ProductPage(long total, int limit, int offset, java.util.List<ProductResponse> results) {
    }
}
