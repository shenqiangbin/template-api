package com.example.demo.config;

import com.zaxxer.hikari.HikariConfig;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component //自动注入
@ConfigurationProperties(prefix = "datasource.mysql")
public class MySQLDataSourceConfig extends HikariConfig {
}
