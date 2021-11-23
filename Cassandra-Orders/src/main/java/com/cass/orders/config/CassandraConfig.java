package com.cass.orders.config;

import java.net.InetSocketAddress;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.cassandra.repository.config.EnableCassandraRepositories;


import com.datastax.oss.driver.api.core.CqlSession;

@EnableCassandraRepositories
@Configuration
public class CassandraConfig{
	
	@Value("${spring.data.cassandra.contact-points}")
	private String cassandraHost;
	
	@Value("${spring.data.cassandra.keyspace-name}")
	private String cassandraKeySpace;
	
	@Value("${spring.data.cassandra.port}")
	private String cassandraPort;
	
	@Value("${spring.data.cassandra.local-datacenter}")
	private String cassandraDataCenter;
	
	public @Bean CqlSession session(){
		
		return CqlSession.builder()
				.addContactPoint(new InetSocketAddress(cassandraHost, Integer.valueOf(cassandraPort)))
				.withKeyspace(cassandraKeySpace)
				.withLocalDatacenter(cassandraDataCenter)
				.build();
		
	}
	
}
