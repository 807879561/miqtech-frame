package com.miqtech.frame.session;

import java.io.Serializable;
import java.util.Set;

import org.apache.commons.lang3.SerializationUtils;
import org.springframework.beans.factory.annotation.Autowired;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

public class RedisService {

	private static Jedis jedis;
	@Autowired
	private JedisPoolConfig jedisPoolConfig;
	private String host;
	private int port;
	private int timeout;
	private String password;

	@SuppressWarnings("resource")
	private Jedis getJedis() {
		if (jedis == null) {
			return new JedisPool(jedisPoolConfig, host, port, timeout, password).getResource();
		}
		return jedis;
	}

	/**
	 * 通过key删除（字节）
	 * @param key
	 */
	public void del(byte[] key) {
		this.getJedis().del(key);
	}

	public void delObj(Serializable key) {
		this.getJedis().del(serialize(key));
	}

	/**
	 * 通过key删除
	 * @param key
	**/
	public void del(String key) {
		this.getJedis().del(key);
	}

	/**
	 * 添加key value 并且设置存活时间(byte)
	 * @param key
	 * @param value
	 * @param liveTime
	 */
	public void set(byte[] key, byte[] value, int liveTime) {
		this.set(key, value);
		this.getJedis().expire(key, liveTime);
	}

	public void setObj(Serializable key, Serializable value, int liveTime) {
		this.set(serialize(key), serialize(value));
		this.getJedis().expire(serialize(key), liveTime);
	}

	public void expire(String key, int liveTime) {
		this.getJedis().expire(key, liveTime);
	}

	/**
	 * 添加key value 并且设置存活时间(秒)
	 * @param key
	 * @param value
	 * @param liveTime
	 */
	public void set(String key, String value, int liveTime) {
		this.set(key, value);
		this.getJedis().expire(key, liveTime);
	}

	/**
	 * 添加key value
	 * @param key
	 * @param value
	 */
	public void set(String key, String value) {
		this.getJedis().set(key, value);
	}

	/**添加key value (字节)(序列化)
	 * @param key
	 * @param value
	 */
	public void set(byte[] key, byte[] value) {
		this.getJedis().set(key, value);
	}

	public void setObj(Serializable key, Serializable value) {
		this.getJedis().set(serialize(key), serialize(value));
	}

	/**
	 * 获取redis value (String)
	 * @param key
	 * @return
	 */
	public String get(String key) {
		String value = this.getJedis().get(key);
		return value;
	}

	/**
	 * 获取redis value (byte [] )(反序列化)
	 * @param key
	 * @return
	 */
	public byte[] get(byte[] key) {
		return this.getJedis().get(key);
	}

	public Object getObj(Serializable key) {
		byte[] value = this.getJedis().get(serialize(key));
		if (value == null) {
			return null;
		}
		return deserialize(value);
	}

	/**
	 * 通过正则匹配keys
	 * @param pattern
	 * @return
	 */
	public Set<String> keys(String pattern) {
		return this.getJedis().keys(pattern);
	}

	/**
	 * 通过正则匹配keys
	 * @param pattern
	 * @return
	 */
	public Set<byte[]> keys(byte pattern[]) {
		return this.getJedis().keys(pattern);
	}

	public Set<byte[]> keyss(Serializable pattern) {
		return this.getJedis().keys(serialize(pattern));
	}

	/**
	 * 检查key是否已经存在
	 * @param key
	 * @return
	 */
	public boolean exists(String key) {
		return this.getJedis().exists(key);
	}

	/**
	 * 检查key是否已经存在
	 * @param key
	 * @return
	 */
	public boolean exists(byte[] key) {
		return this.getJedis().exists(key);
	}

	public boolean existss(Serializable key) {
		return this.getJedis().exists(serialize(key));
	}

	/**
	 * 清空redis 所有数据
	 * @return
	 */
	public String flushDB() {
		return this.getJedis().flushDB();
	}

	/**
	 * 查看redis里有多少数据
	 */
	public long dbSize() {
		return this.getJedis().dbSize();
	}

	/**
	 * 检查是否连接成功
	 * @return
	 */
	public String ping() {
		return this.getJedis().ping();
	}

	public Long incr(String key) {
		return this.getJedis().incr(key);
	}

	//序列化
	public byte[] serialize(Serializable obj) {
		return SerializationUtils.serialize(obj);
	}

	//反序列化
	public Object deserialize(byte[] obj) {
		return SerializationUtils.deserialize(obj);
	}

	public JedisPoolConfig getJedisPoolConfig() {
		return jedisPoolConfig;
	}

	public void setJedisPoolConfig(JedisPoolConfig jedisPoolConfig) {
		this.jedisPoolConfig = jedisPoolConfig;
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public int getTimeout() {
		return timeout;
	}

	public void setTimeout(int timeout) {
		this.timeout = timeout;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public static void setJedis(Jedis jedis) {
		RedisService.jedis = jedis;
	}

}
