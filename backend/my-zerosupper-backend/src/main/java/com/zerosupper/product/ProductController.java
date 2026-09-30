package com.zerosupper.product;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProductController {
    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/api/products")
    ProductService.ProductPage list(
            @RequestParam(required = false) String category,
            @RequestParam(required = false) String search,
            @RequestParam(defaultValue = "createdAt") String orderBy,
            @RequestParam(defaultValue = "desc") String sort,
            @RequestParam(defaultValue = "20") int limit,
            @RequestParam(defaultValue = "0") int offset) {
        return productService.list(category, search, orderBy, sort, limit, offset);
    }

    @PostMapping("/api/admin/products")
    ResponseEntity<ProductResponse> create(@Valid @RequestBody ProductInput input) {
        return ResponseEntity.status(HttpStatus.CREATED).body(productService.create(input));
    }

    @PutMapping("/api/admin/products/{id}")
    ProductResponse update(@PathVariable Long id, @Valid @RequestBody ProductInput input) {
        return productService.update(id, input);
    }

    @DeleteMapping("/api/admin/products/{id}")
    ResponseEntity<Void> delete(@PathVariable Long id) {
        productService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
