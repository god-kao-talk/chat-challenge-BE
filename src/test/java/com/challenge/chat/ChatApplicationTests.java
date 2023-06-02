package com.challenge.chat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles({"test"})
class ChatApplicationTests {
	@Test
	@DisplayName("통합 테스트 성공")
	void contextLoads() {
	}
}
