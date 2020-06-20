package com.haochen.learning.zookeeper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;

public class DistributeClient {

	public static void main(String[] args) throws IOException, KeeperException, InterruptedException {
		
		DistributeClient client = new DistributeClient();
		
		// 1 获取zookeeper集群连接
		client.getConnect();
		
		// 2 注册监听
		client.getChlidren();
		
		// 3 业务逻辑处理
		client.business();
		
	}

	private void business() throws InterruptedException {
		Thread.sleep(Long.MAX_VALUE);
	}

	private void getChlidren() throws KeeperException, InterruptedException {
		
		List<String> children = zkClient.getChildren("/test/servers", true);
		
		// 存储服务器节点主机名称集合
		ArrayList<String> hosts = new ArrayList<>();
		
		for (String child : children) {
			
			byte[] data = zkClient.getData("/test/servers/"+child, false, null);
			
			hosts.add(new String(data));
		}
		
		// 将所有在线主机名称打印到控制台
		System.out.println(hosts);
		
	}

	private String connectString = "node01:2181,node02:2181,node03:2181";
	private int sessionTimeout = 2000;
	private ZooKeeper zkClient;

	private void getConnect() throws IOException {
	
		zkClient = new ZooKeeper(connectString , sessionTimeout , new Watcher() {
			
			@Override
			public void process(WatchedEvent event) {
				
				try {
					getChlidren();
				} catch (KeeperException e) {
					e.printStackTrace();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		});
		
	}
}
