package com.taotao.rest.jedis;

import java.io.IOException;
import java.util.HashSet;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisPool;

public class JedisTest {
	//单机版本测试

	public void testJedisSingle(){
		//创建一个jedis的对象
		Jedis jedis = new Jedis("192.168.192.128",6379);
		
		//调用jedis对象的方法，方法的名称和jedis的命令一致
		jedis.set("key1","jedis test");
		String string = jedis.get("key1");
		System.out.println(string);
		//关闭jedis
		jedis.close();
		
	}

	//redis单机版连接池的使用
	public void testJedisPool(){
		//创建jedis连接池
	JedisPool jedisPool = new JedisPool("192.168.192.128",6379);
	   //从连接池获取jedis对象
	Jedis jedis = jedisPool.getResource();
	String string = jedis.get("key1");
	System.out.println(string);
	//关闭jedis对象
	jedis.close();
	jedisPool.close();
	
	}
	
	@Test
	//redis集群版本测试
	public void testJedisCluster() throws Exception{
		HashSet<HostAndPort> nodes = new HashSet<>();
		nodes.add(new HostAndPort("192.168.192.128",7001));
		nodes.add(new HostAndPort("192.168.192.128",7002));
		nodes.add(new HostAndPort("192.168.192.128",7003));
		nodes.add(new HostAndPort("192.168.192.128",7004));
		nodes.add(new HostAndPort("192.168.192.128",7005));
		nodes.add(new HostAndPort("192.168.192.128",7006));
		JedisCluster jedisCluster = new JedisCluster(nodes);
		jedisCluster.set("key1", "1000");
		String string = jedisCluster.get("key1");
		System.out.println(string);
		jedisCluster.close();
	}
	
	
	//redis配置在spring，单机版测试
	public void testSpringJedisSingle(){
		
		ApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:spring/applicationContext-*.xml");
		JedisPool pool = (JedisPool) applicationContext.getBean("redisClient");
		Jedis jedis = pool.getResource();
		String string = jedis.get("key1");
		System.out.println(string);
		jedis.close();
		pool.close();

	}
	
	//redis配置在spring，集群版测试
	@Test
	public void testSpringJedisCluster() throws Exception {
		ApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:spring/applicationContext-*.xml");
		JedisCluster jedisCluster =  (JedisCluster) applicationContext.getBean("redisClient");
		jedisCluster.set("key1","1225");
		String string = jedisCluster.get("key1");
		System.out.println(string);
		jedisCluster.close();
	}


}
