// package com.challenge.chat.config;
//
// import org.elasticsearch.client.RestHighLevelClient;
// import org.springframework.context.annotation.Configuration;
// import org.springframework.data.elasticsearch.client.ClientConfiguration;
// import org.springframework.data.elasticsearch.client.RestClients;
// import org.springframework.data.elasticsearch.config.AbstractElasticsearchConfiguration;
// import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;
//
// import com.challenge.chat.domain.chat.repository.ChatSearchRepository;
//
// @Configuration
// @EnableElasticsearchRepositories(basePackageClasses = {ChatSearchRepository.class})
// public class ElasticSearchConfig extends AbstractElasticsearchConfiguration {
// 	@Override
// 	public RestHighLevelClient elasticsearchClient() {
// 		// http port 와 통신할 주소
// 		ClientConfiguration configuration = ClientConfiguration.builder()
// 			.connectedTo("es:9200")
// 			.build();
// 		return RestClients.create(configuration).rest();
// 	}
// }
