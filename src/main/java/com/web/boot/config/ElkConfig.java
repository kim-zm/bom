package com.web.boot.config;

import java.net.InetAddress;

import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.TransportAddress;
import org.elasticsearch.transport.client.PreBuiltTransportClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;

@Configuration
@EnableElasticsearchRepositories(basePackages="com.web.boot.repository")
public class ElkConfig {
	@Value("${spring.data.elasticsearch.cluster-name}")
	private String EClusterName;
	@Value("${elasticsearch.host}")
	private String EHost;	
	@Value("${elasticsearch.port}")
	private int EPort;
	
	@Bean
	public Client client() throws Exception {
		Settings ESettings = Settings.builder()
							 .put("cluster.name", EClusterName).build();
		
		TransportClient client = new PreBuiltTransportClient(ESettings);
		client.addTransportAddress(new TransportAddress(InetAddress.getByName(EHost), EPort));        
		return client;
	}

	@Bean
	public ElasticsearchOperations elasticsearchTemplate() throws Exception {
		return new ElasticsearchTemplate(client());
	}
}
