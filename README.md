# learning
学无止境
## 一、Zookeeper
#### Zookeeper中的配置文件zoo.cfg中参数含义解读如下：
- 1．tickTime =2000：通信心跳数，Zookeeper服务器与客户端心跳时间，单位毫秒
Zookeeper使用的基本时间，服务器之间或客户端与服务器之间维持心跳的时间间隔，也就是每个tickTime时间就会发送一个心跳，时间单位为毫秒。
它用于心跳机制，并且设置最小的session超时时间为两倍心跳时间。(session的最小超时时间是2*tickTime)
- 2．initLimit =10：LF初始通信时限
集群中的Follower跟随者服务器与Leader领导者服务器之间初始连接时能容忍的最多心跳数（tickTime的数量），用它来限定集群中的Zookeeper服务器连接到Leader的时限。
- 3．syncLimit =5：LF同步通信时限
集群中Leader与Follower之间的最大响应时间单位，假如响应超过syncLimit * tickTime，Leader认为Follwer死掉，从服务器列表中删除Follwer。
- 4．dataDir：数据文件目录+数据持久化路径
主要用于保存Zookeeper中的数据。
- 5．clientPort =2181：客户端连接端口
监听客户端连接的端口。

#### 选举机制（面试重点）
- 1）半数机制：集群中半数以上机器存活，集群可用。所以Zookeeper适合安装奇数台服务器。
- 2）Zookeeper虽然在配置文件中并没有指定Leader和Follower。但是，Zookeeper工作时，是有一个节点为Leader，其他则为Follower，Leader是通过内部的选举机制临时产生的。
- 3）以一个简单的例子来说明整个选举的过程。
- 假设有五台服务器组成的Zookeeper集群，它们的id从1-5，同时它们都是最新启动的，也就是没有历史数据，在存放数据量这一点上，都是一样的。假设这些服务器依序启动
-（1）服务器1启动，此时只有它一台服务器启动了，它发出去的报文没有任何响应，所以它的选举状态一直是LOOKING状态。
- （2）服务器2启动，它与最开始启动的服务器1进行通信，互相交换自己的选举结果，由于两者都没有历史数据，所以id值较大的服务器2胜出，但是由于没有达到超过半数以上的服务器都同意选举它(这个例子中的半数以上是3)，所以服务器1、2还是继续保持LOOKING状态。
- （3）服务器3启动，根据前面的理论分析，服务器3成为服务器1、2、3中的老大，而与上面不同的是，此时有三台服务器选举了它，所以它成为了这次选举的Leader。
- （4）服务器4启动，根据前面的分析，理论上服务器4应该是服务器1、2、3、4中最大的，但是由于前面已经有半数以上的服务器选举了服务器3，所以它只能接收当小弟的命了。
- （5）服务器5启动，同4一样当小弟。

#### ZooKeeper的监听原理是什么

#### ZooKeeper的部署方式有哪几种？集群中的角色有哪些？集群最少需要几台机器？
- （1）部署方式单机模式、集群模式
- （2）角色：Leader和Follower, 还有Observer? 但是Observer不参与选举。
- （3）集群最少需要机器数：3

## 二、Kafka
