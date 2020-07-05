package com.bigdata.bigdata.config;

import org.apache.http.ssl.SSLContextBuilder;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.client.ClientConfiguration;
import org.springframework.data.elasticsearch.client.RestClients;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;

import javax.net.ssl.SSLContext;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;

@Configuration
public class ElasticsSearchConfig {
    private final String baseUrl;
    private final int port;
    private final boolean ssl;
    private final String username;
    private final String password;

    @Autowired
    public ElasticsSearchConfig(@Value("${elastics.baseurl:127.0.0.1}") String baseUrl,
                                @Value("${elastics.port:9200}") int port,
                                @Value("${elastics.ssl:false}") boolean ssl,
                                @Value("${elastics.basicAuth.username:}") String username,
                                @Value("${elastics.basicAuth.password:") String password) {
        this.baseUrl = baseUrl;
        this.port = port;
        this.ssl = ssl;
        this.username = username;
        this.password = password;
    }

    @Bean
    public RestHighLevelClient client() {
        SSLContext sslContext = null;
        try {
            sslContext = new SSLContextBuilder().build();
        } catch (NoSuchAlgorithmException | KeyManagementException e) {
            e.printStackTrace();
        }
        ClientConfiguration.TerminalClientConfigurationBuilder terminalClientConfigurationBuilder = null;
        ClientConfiguration.MaybeSecureClientConfigurationBuilder maybeSecureClientConfigurationBuilder = ClientConfiguration
                .builder()
                .connectedTo(baseUrl + ":" + port);
        if (ssl && sslContext != null) {
            terminalClientConfigurationBuilder = maybeSecureClientConfigurationBuilder.usingSsl(sslContext);
            if (!username.isEmpty() || !password.isEmpty()) {
                terminalClientConfigurationBuilder = terminalClientConfigurationBuilder.withBasicAuth(username, password);
            }
            return RestClients.create(terminalClientConfigurationBuilder.build()).rest();
        }
        if (!username.isEmpty() || !password.isEmpty()) {
            terminalClientConfigurationBuilder = maybeSecureClientConfigurationBuilder.withBasicAuth(username, password);
            return RestClients.create(terminalClientConfigurationBuilder.build()).rest();
        }

        return RestClients.create(maybeSecureClientConfigurationBuilder.build()).rest();
    }

    @Bean
    public ElasticsearchOperations elasticsearchTemplate() {
        return new ElasticsearchRestTemplate(client());
    }
}
