package com.ry.es.esdemo.config;

import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.TransportAddress;
import org.elasticsearch.transport.client.PreBuiltTransportClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.net.InetAddress;
import java.net.UnknownHostException;

@Configuration
public class SearchConfig {

    @Autowired
    EsConfig esConfig;

    @Bean
    public TransportClient client() throws UnknownHostException {
        TransportAddress node = new TransportAddress(
                InetAddress.getByName(esConfig.getIp()),
                esConfig.getPort()
        );
        Settings settings = Settings.builder()
                .put("cluster.name", esConfig.getClusterName())

                .build();
/**
 * 配置忽略集群名校验。client.transport.ignore_cluster_name设置为 true
 *
 * Settings settings = Settings.settingsBuilder()
 *                         .put("client.transport.sniff", true)
 *                         .put("client.transport.ignore_cluster_name", true)
 *                         .build();
 */
        TransportClient client = new PreBuiltTransportClient(settings);
        client.addTransportAddress(node);
        return client;
    }
}
