package ru.mail.ales2003.deals2017.webapp.jedis;

import org.springframework.stereotype.Component;

import redis.clients.jedis.Jedis;

@Component
public class JedisCache {

	public static final Jedis JEDIS = new Jedis("localhost");

	public void addToCache(String login, Integer id, String password, int sec) {
		addPasswordToCache(login, password);
		addIdToCache(login, id);
		addExistTime(login, sec);
	}
	
	public Integer getIdFromCache(String login) {
		String stringId = JEDIS.lindex(login, 0);
		Integer integerId = Integer.valueOf(stringId);
		return integerId;
	}

	public String getPasswordFromCache(String login) {
		String password = JEDIS.lindex(login, 1);
		return password;
	}

	public boolean isExistInCache(String login) {
		return JEDIS.exists(login) ? true : false;
	}

	public Long getLength(String login) {

		return JEDIS.llen(login);
	}
	
	public void cleanUserData(String login, Integer id, String password) {
		clearId(login, id);
		clearPassword(login, password);
	}
	
	private void addIdToCache(String login, Integer id) {
		String stringId = id.toString();
		JEDIS.lpush(login, stringId);
	}

	private void addPasswordToCache(String login, String password) {
		JEDIS.lpush(login, password);
	}

	private void addExistTime(String login, int sec) {
		JEDIS.expire(login, sec);
	}



	private void clearId(String login, Integer id) {
		String stringId = id.toString();
		JEDIS.lrem(login, 0, stringId);
	}

	private void clearPassword(String login, String password) {
		JEDIS.lrem(login, 0, password);
	}

	
}
