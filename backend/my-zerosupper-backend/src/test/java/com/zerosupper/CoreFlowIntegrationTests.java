package com.zerosupper;

import static org.assertj.core.api.Assertions.assertThat;

import com.zerosupper.auth.AuthService;
import com.zerosupper.order.OrderItemRequest;
import com.zerosupper.order.OrderRequest;
import com.zerosupper.order.OrderResponse;
import com.zerosupper.order.OrderService;
import com.zerosupper.product.ProductService;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(properties = {
        "spring.datasource.url=jdbc:h2:mem:zerosupper-flow-test;DB_CLOSE_DELAY=-1",
        "app.admin.email=admin@flow.test",
        "app.admin.password=Testing-Admin-Password-123"
})
class CoreFlowIntegrationTests {
    @Autowired AuthService authService;
    @Autowired ProductService productService;
    @Autowired OrderService orderService;

    @Test
    void memberCanRegisterLoginAndPlaceAnOrder() {
        String email = "guest-" + UUID.randomUUID() + "@example.com";
        var user = authService.register(email, "A-safe-test-password-123");
        var login = authService.login(email, "A-safe-test-password-123");
        assertThat(login.token().value()).isNotBlank();

        var products = productService.list("FOOD", null, "createdAt", "desc", 20, 0);
        assertThat(products.results()).isNotEmpty();

        OrderResponse order = orderService.create(user.getId(), new OrderRequest(
                List.of(new OrderItemRequest(products.results().get(0).productId(), 2)),
                LocalDate.now().plusDays(1), LocalTime.of(18, 30), "0912-345-678"));

        assertThat(order.userEmail()).isEqualTo(email);
        assertThat(order.items()).hasSize(1);
        assertThat(order.totalAmount()).isPositive();
        assertThat(orderService.mine(user.getId())).extracting(OrderResponse::orderId)
                .contains(order.orderId());
    }
}
