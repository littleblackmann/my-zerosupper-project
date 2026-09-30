package com.zerosupper;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(properties = {
        "spring.datasource.url=jdbc:h2:mem:zerosupper-test;DB_CLOSE_DELAY=-1",
        "app.admin.email=admin@test.local",
        "app.admin.password=Testing-Admin-Password-123"
})
class ZeroSupperApplicationTests {
    @Test
    void contextLoads() {
    }
}
