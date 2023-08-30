package com.uin.zookeepercurator.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author dingchuan
 */
@Data
@Component
@ConfigurationProperties(prefix = "curator")
public class WrapperZKConfig {

  private int retryCount;

  private int elapsedTimeMs;

  private String connectionString;

  private int sessionTimeoutMs;

  private int connectionTimeoutMs;
}
