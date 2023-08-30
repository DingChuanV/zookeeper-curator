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
public class ZookeeperProperties {

  /**
   * 最大重试次数
   */
  private int maxRetries;
  /**
   * 重试间隔时间，毫秒
   */
  private int elapsedTimeMs;
  /**
   * 连接地址
   */
  private String connectAddress;
  /**
   * 会话超时时间，毫秒
   * <p>
   * 只要在SessionTimeout超时时间内能够重新连接上集群中任意一台服务器，那么之前创建的会话仍然有效
   */
  private int sessionTimeoutMs;
  /**
   * 连接超时时间，毫秒
   */
  private int connectionTimeoutMs;
}
