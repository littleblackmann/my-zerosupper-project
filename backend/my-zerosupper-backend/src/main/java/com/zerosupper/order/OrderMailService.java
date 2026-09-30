package com.zerosupper.order;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class OrderMailService {
    private static final Logger log = LoggerFactory.getLogger(OrderMailService.class);
    private final ObjectProvider<JavaMailSender> mailSender;
    private final boolean enabled;
    private final String from;

    public OrderMailService(ObjectProvider<JavaMailSender> mailSender,
                            @Value("${app.mail.enabled:false}") boolean enabled,
                            @Value("${app.mail.from:noreply@zerosupper.local}") String from) {
        this.mailSender = mailSender;
        this.enabled = enabled;
        this.from = from;
    }

    public void sendOrderConfirmation(CustomerOrder order) {
        if (!enabled) {
            return;
        }
        JavaMailSender sender = mailSender.getIfAvailable();
        if (sender == null) {
            log.warn("Order email is enabled but no mail sender is configured");
            return;
        }
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom(from);
            message.setTo(order.getUser().getEmail());
            message.setSubject("ZERO Supper 訂單 #" + order.getId());
            message.setText("我們已收到你的訂單。到店時間：" + order.getArrivalDate() + " "
                    + order.getArrivalTime() + "，總金額：NT$ " + order.getTotalAmount());
            sender.send(message);
        } catch (RuntimeException exception) {
            log.error("Failed to send confirmation for order {}", order.getId(), exception);
        }
    }
}
