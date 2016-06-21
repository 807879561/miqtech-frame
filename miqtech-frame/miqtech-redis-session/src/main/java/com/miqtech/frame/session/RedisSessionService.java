package com.miqtech.frame.session;

import java.io.Serializable;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RedisSessionService {

	@Autowired
	protected RedisService redisService;

	/**存放序列化对象
	 * @param key
	 * @param value
	 * @param liveTime(单位秒,-1表示永远有效,0表示过期)
	 */
	public void setAttributeObj(String key, Serializable value, int liveTime) {
		redisService.setObj(key, value, liveTime);
	}

	/**存放字符串
	 * @param key
	 * @param value
	 * @param liveTime(单位秒,-1表示永远有效,0表示过期)
	 */
	public void setAttribute(String key, String value, int liveTime) {
		redisService.set(key, value, liveTime);
	}

	/**获得存放的序列化对象
	 * @param key
	 * @return
	 */
	public Object getAttributeObj(String key) {
		return redisService.getObj(key);
	}

	/**获得存放的字符串
	 * @param key
	 * @return
	 */
	public String getAttribute(String key) {
		return redisService.get(key);
	}

}
