package com.bigdata.bigdata.config;

import com.datastax.oss.driver.api.core.CqlSession;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.cassandra.config.AbstractCassandraConfiguration;
import org.springframework.data.cassandra.config.SchemaAction;

import java.net.InetSocketAddress;

public class CassandraConfig extends AbstractCassandraConfiguration {

    @Value("${spring.data.cassandra.contact-points:placeholder}")
    private String contactPoints;

    @Value("${spring.data.cassandra.port:0000}")
    private int port;

    @Value("${spring.data.cassandra.keyspace-name:placeholder}")
    private String keySpace;

    @Value("${spring.data.cassandra.schema-action}")
    private String schemaAction;

    @Value("${spring.data.cassandra.local-datacenter}")
    private String localDataCenter;

    @Override
    protected String getKeyspaceName() {
        return keySpace;
    }

    @Override
    protected String getContactPoints() {
        return contactPoints;
    }

    @Override
    protected int getPort() {
        return port;
    }

    @Override
    public SchemaAction getSchemaAction() {
        return SchemaAction.valueOf(schemaAction);
    }

    @Override
    protected String getLocalDataCenter() {
        return localDataCenter;
    }
}

//@Configuration
//public class CassandraConfig {
//
//    @Value("${spring.data.cassandra.contact-points:placeholder}")
//    private String contactPoints;
//
//    @Value("${spring.data.cassandra.port:0000}")
//    private int port;
//
//    @Value("${spring.data.cassandra.keyspace-name:placeholder}")
//    private String keySpace;
//
//    @Value("${spring.data.cassandra.schema-action}")
//    private String schemaAction;
//
//    @Value("${spring.data.cassandra.local-datacenter}")
//    private String localDataCenter;
//
//    public @Bean CqlSession session() {
//        return CqlSession.builder()
//                .withKeyspace(keySpace)
//                .withLocalDatacenter("datacenter1")
//                .addContactPoint(new InetSocketAddress(contactPoints, port))
//                .build();
//    }
//
//}
