package com.uin.zookeepercurator;

import cn.hutool.json.JSON;
import cn.hutool.json.JSONUtil;
import java.util.Arrays;
import javax.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.cache.NodeCache;
import org.apache.curator.framework.recipes.cache.NodeCacheListener;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.data.Stat;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Slf4j
class ZookeeperCuratorApplicationTests {

  @Resource
  private CuratorFramework curator;

  /**
   * 创建节点
   *
   * @throws Exception
   */
  @Test
  void createNode() throws Exception {
    /*
     * 添加持久节点 默认
     * */
    // String s1 = curator.create().forPath("/dev");
    // System.out.println(String.format("curator create node :%s successfully.", s1));

    // 创建临时序号节点
    String s = curator.create().withMode(CreateMode.EPHEMERAL_SEQUENTIAL)
        .forPath("/localdev", "some-data".getBytes());
    System.out.printf("curator create node :%s successfully.%n", s);

    System.in.read();
  }

  /**
   * 获取节点的值
   */
  @Test
  void getNodeData() {
    try {
      byte[] bytes = curator.getData().forPath("/dev");
      System.out.println("bytes:[]" + Arrays.toString(bytes));
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  @Test
  void setNodeData() {
    Stat stat = null;
    try {
      stat = curator.setData().forPath("/dev", "newMessage".getBytes());
      log.info("stat:{}", JSONUtil.parse(stat));
      byte[] bytes = curator.getData().forPath("/dev");
      log.info("localdev data:{}", Arrays.toString(bytes));
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  @Test
  void createWithParent() throws Exception {
    String pathWithParent = "/localdev/sub-node-1";
    // 创建多级节点
    String path = curator.create().creatingParentContainersIfNeeded().forPath(pathWithParent);
    System.out.printf("curator create node :%s success!%n", path);
  }

  @Test
  void deleteWithChild() throws Exception {
    String path = "/localdev";
    //删除节点的同时一并删除子节点
    curator.delete().guaranteed().deletingChildrenIfNeeded().forPath(path);
  }

  /**
   * 监听节点的数据的变化
   */
  @Test
  void addNodeListener() {
    NodeCache nodeCache = new NodeCache(curator, "/dev");
    nodeCache.getListenable().addListener(new NodeCacheListener() {
      @Override
      public void nodeChanged() throws Exception {
        log.info("{} path nodechanged", "/dev");
        printNodeData();
      }
    });
    try {
      nodeCache.start();
      System.in.read();
    } catch (Exception e) {
      throw new RuntimeException(e);
    }

  }

  private void printNodeData() {
    byte[] bytes = new byte[0];
    try {
      bytes = curator.getData().forPath("/dev");
      log.info("bytes : {}", Arrays.toString(bytes));
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  @Test
  void contextLoads() {
  }

}
