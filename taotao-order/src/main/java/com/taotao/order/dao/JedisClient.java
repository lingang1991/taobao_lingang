package com.taotao.order.dao;

public interface JedisClient {
	String set(String key,String value);
    String get(String key);
    long hset(String hkey,String key,String value);//哈希值
    String hget(String hkey,String key);
    long incr(String key);//键自增长
    long expire(String key, int second);//设置key的有效时间
    long ttl(String key);//查询key是否过期
    long del(String key);// 删除key
    long hdel(String hkey,String key);
} 
