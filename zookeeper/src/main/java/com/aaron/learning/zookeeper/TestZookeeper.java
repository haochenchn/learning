package com.aaron.learning.zookeeper;

import java.io.IOException;
import java.util.List;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooDefs.Ids;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.Stat;
import org.junit.Before;
import org.junit.Test;

public class TestZookeeper {

	private String connectString="node01:2181,node02:2181,node03:2181";
	private int sessionTimeout = 2000;
	private ZooKeeper zkClient;

	@Before
	public void init() throws IOException{
		
		zkClient = new ZooKeeper(connectString, sessionTimeout , new Watcher() {
			
			public void process(WatchedEvent event) {
				
				System.out.println("---------start----------");
				List<String> children;
				try {
					children = zkClient.getChildren("/test", true);

					for (String child : children) {
						System.out.println(child);
					}
					System.out.println("---------end----------");
				} catch (KeeperException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
	}
	
	// 1 创建节点
	@Test
	public void createNode() throws KeeperException, InterruptedException{
		
		String path = zkClient.create("/test/servers", "no".getBytes(), Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
		
		System.out.println(path);
		
	}
	
	// 2 获取子节点 并监控节点的变化
	@Test
	public void getDataAndWatch() throws KeeperException, InterruptedException{
		
		List<String> children = zkClient.getChildren("/test", true);
		
		for (String child : children) {
			System.out.println(child);
		}
		
		Thread.sleep(Long.MAX_VALUE);
	}
	
	// 3 判断节点是否存在
	@Test
	public void exist() throws KeeperException, InterruptedException{
		
		Stat stat = zkClient.exists("/test", false);
		
		System.out.println(stat==null? "not exist":"exist");
	}
}
