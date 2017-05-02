package ru.mail.ales2003.deals2017.webapp.samples;

import java.util.HashMap;
import java.util.Map;

import redis.clients.jedis.Jedis;

public class RedisDemo {
	public static void main(String[] args) {

		String login = "admin1";
		Map<String, String> idPassword = new HashMap<>();
		String id = "id";
		String password = "password";
		idPassword.put(login, id);
		idPassword.put(id, password);

		try {
			// Connecting to Redis server on localhost
			Jedis jedis = new Jedis("localhost");
			System.out.println("Connection to server sucessfully");
			// check whether server is running or not
			System.out.println("Server is running: Ping " + jedis.ping());

			// with info-method
			System.out.println("Server info " + jedis.info());

			// work with list
			System.out.println("List push key value: " + jedis.lpush("key", "value"));
			System.out.println("List pop: " + jedis.lpop("key"));

			// work with hash
			System.out.println("Map set key map: " + jedis.hmset(login, idPassword));
			// set storeTime
			jedis.expire(login, 10);
			Thread.sleep(5000);
			// in 5 sec
			System.out.println("Map get id: " + jedis.hmget("admin", "admin"));
			System.out.println("Map get password: " + jedis.hmget("admin", "id"));

			Thread.sleep(5000);
			// in 10 sec
			System.out.println("Map get: " + jedis.hmget(login, id));

			// System.out.println("Map get: " + jedis.hmget("login+1", id));

			Jedis people = new Jedis("localhost");
			System.out.println("result lenght: " + (people.llen("люди")));
			people.rpush("люди", "Мэри");

			jedis.rpush("люди", "Марк");
			jedis.expire("люди", 6);
			Thread.sleep(5000);
			System.out.println("result lenght: " + (people.llen("люди")));
			if (people.llen("люди") > 0) {
				System.out.println("result: " + people.lindex("люди", 0).equals("Мэри"));
				String s = people.lindex("люди", 0);
				System.out.println(s);
				System.out.println("result: " + people.lindex("люди", 1).equals("Марк"));
			}
			Thread.sleep(5000);
			System.out.println("result lenght: " + (people.llen("люди")));
			if (people.llen("люди") > 0) {
				System.out.println("result: " + people.lindex("люди", 0).equals("Мэри"));
				System.out.println("result: " + people.lindex("люди", 1).equals("Марк"));
			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
