package com.zerosupper.order;

import com.zerosupper.security.CurrentUser;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class OrderController {
    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping("/orders")
    ResponseEntity<OrderResponse> create(@AuthenticationPrincipal CurrentUser user,
                                         @Valid @RequestBody OrderRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(orderService.create(user.id(), request));
    }

    @GetMapping("/orders/me")
    java.util.List<OrderResponse> mine(@AuthenticationPrincipal CurrentUser user) {
        return orderService.mine(user.id());
    }

    @GetMapping("/admin/orders")
    OrderService.OrderPage all(@RequestParam(defaultValue = "50") int limit,
                               @RequestParam(defaultValue = "0") int offset) {
        return orderService.all(limit, offset);
    }

    @PatchMapping("/admin/orders/{id}/status")
    OrderResponse updateStatus(@PathVariable Long id, @Valid @RequestBody StatusRequest request) {
        return orderService.updateStatus(id, request.status());
    }

    public record StatusRequest(@NotNull OrderStatus status) {
    }
}
