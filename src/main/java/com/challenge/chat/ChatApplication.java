package com.challenge.chat;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
// import org.springframework.data.elasticsearch.config.EnableElasticsearchAuditing;
import org.springframework.data.cassandra.config.EnableCassandraAuditing;

// import com.challenge.chat.domain.chat.repository.ChatSearchRepository;

// @EnableJpaAuditing
// @EnableElasticsearchAuditing
@SpringBootApplication
@EnableCassandraAuditing
public class ChatApplication {

	public static void main(String[] args) {
		SpringApplication.run(ChatApplication.class, args);
	}
}
