package com.uin.zookeepercurator.config;

import lombok.AllArgsConstructor;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.RetryNTimes;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author dingchuan
 */
@AllArgsConstructor
@Configuration
public class CuratorConfig {

  private final ZookeeperProperties zookeeperProperties;

  @Bean(initMethod = "start")
  public CuratorFramework curatorFramework() {
    return CuratorFrameworkFactory.newClient(
        zookeeperProperties.getConnectAddress(),
        zookeeperProperties.getSessionTimeoutMs(), zookeeperProperties.getConnectionTimeoutMs(),
        new RetryNTimes(zookeeperProperties.getMaxRetries(),
            zookeeperProperties.getElapsedTimeMs()));
  }

}
