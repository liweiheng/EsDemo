package com.ry.es.esdemo.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.format.annotation.NumberFormat;
import org.springframework.stereotype.Component;

/**
 * Created by lwh
 * 读取es配置
 */
@Component
@PropertySource("classpath:es_config.properties")
@Data
public class EsConfig {
    @Value("${es.cluster.name}")
    private String clusterName;//集群名称
    @Value("${es.host.ip}")
    private String ip;
    @Value("${es.host.port}")
    private int port;

}
