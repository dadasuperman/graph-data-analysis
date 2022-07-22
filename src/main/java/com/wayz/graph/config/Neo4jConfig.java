package com.wayz.graph.config;

import org.neo4j.driver.AuthTokens;
import org.neo4j.driver.Driver;
import org.neo4j.driver.GraphDatabase;
import org.neo4j.driver.Session;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.neo4j.repository.config.EnableNeo4jRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @Auther: zd
 * @Description: config
 * @Date: 2022/7/22 15:12
 */
@Configuration
@EnableNeo4jRepositories("com.wayz.graph.mapper")
@EnableTransactionManagement
public class Neo4jConfig {
    @Value("${spring.neo4j.authentication.url}")
    private String url;
    @Value("${spring.neo4j.authentication.username}")
    private String userName;
    @Value("${spring.neo4j.authentication.password}")
    private String password;
    @Bean(name = "session")
    public Session neo4jSession() {
        Driver driver = GraphDatabase.driver(url, AuthTokens.basic(userName, password));
        return driver.session();
    }
}
