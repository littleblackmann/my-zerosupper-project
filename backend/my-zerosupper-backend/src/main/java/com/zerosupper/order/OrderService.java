package com.zerosupper.order;

import com.zerosupper.common.ApiException;
import com.zerosupper.product.Product;
import com.zerosupper.product.ProductRepository;
import com.zerosupper.user.UserAccount;
import com.zerosupper.user.UserRepository;
import java.time.LocalDate;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OrderService {
    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;
    private final OrderMailService mailService;

    public OrderService(OrderRepository orderRepository, ProductRepository productRepository,
                        UserRepository userRepository, OrderMailService mailService) {
        this.orderRepository = orderRepository;
        this.productRepository = productRepository;
        this.userRepository = userRepository;
        this.mailService = mailService;
    }

    @Transactional
    public OrderResponse create(Long userId, OrderRequest request) {
        if (request.arrivalDate().isBefore(LocalDate.now())) {
            throw new ApiException(HttpStatus.BAD_REQUEST, "INVALID_ARRIVAL_DATE", "到店日期不能早於今天。");
        }
        Map<Long, Integer> quantities = new LinkedHashMap<>();
        for (OrderItemRequest item : request.items()) {
            quantities.merge(item.productId(), item.quantity(), Integer::sum);
        }
        if (quantities.size() > 50) {
            throw new ApiException(HttpStatus.BAD_REQUEST, "TOO_MANY_ITEMS", "單筆訂單最多包含 50 種商品。");
        }

        UserAccount user = userRepository.findById(userId)
                .orElseThrow(() -> new ApiException(HttpStatus.UNAUTHORIZED, "USER_NOT_FOUND", "登入帳號不存在。"));
        CustomerOrder order = new CustomerOrder(user, request.arrivalDate(), request.arrivalTime(),
                request.phoneNumber().trim());

        quantities.forEach((productId, quantity) -> {
            if (quantity < 1 || quantity > 99) {
                throw new ApiException(HttpStatus.BAD_REQUEST, "INVALID_QUANTITY", "商品數量必須介於 1 到 99。");
            }
            Product product = productRepository.findActiveByIdForUpdate(productId)
                    .orElseThrow(() -> new ApiException(HttpStatus.NOT_FOUND, "PRODUCT_NOT_FOUND",
                            "找不到商品：" + productId));
            if (product.getStock() < quantity) {
                throw new ApiException(HttpStatus.CONFLICT, "INSUFFICIENT_STOCK",
                        product.getProductName() + " 的庫存不足。");
            }
            product.reduceStock(quantity);
            order.addItem(product.getId(), product.getProductName(), product.getPrice(), quantity);
        });

        CustomerOrder saved = orderRepository.save(order);
        mailService.sendOrderConfirmation(saved);
        return OrderResponse.from(saved);
    }

    @Transactional(readOnly = true)
    public List<OrderResponse> mine(Long userId) {
        return orderRepository.findByUserIdOrderByCreatedAtDesc(userId).stream()
                .map(OrderResponse::from)
                .toList();
    }

    @Transactional(readOnly = true)
    public OrderPage all(int limit, int offset) {
        int safeLimit = Math.max(1, Math.min(limit, 100));
        int safeOffset = Math.max(offset, 0);
        var page = orderRepository.findAll(PageRequest.of(safeOffset / safeLimit, safeLimit,
                Sort.by(Sort.Direction.DESC, "createdAt")));
        return new OrderPage(page.getTotalElements(), safeLimit, safeOffset,
                page.getContent().stream().map(OrderResponse::from).toList());
    }

    @Transactional
    public OrderResponse updateStatus(Long orderId, OrderStatus status) {
        CustomerOrder order = orderRepository.findById(orderId)
                .orElseThrow(() -> new ApiException(HttpStatus.NOT_FOUND, "ORDER_NOT_FOUND", "找不到訂單。"));
        order.setStatus(status);
        return OrderResponse.from(order);
    }

    public record OrderPage(long total, int limit, int offset, List<OrderResponse> results) {
    }
}
