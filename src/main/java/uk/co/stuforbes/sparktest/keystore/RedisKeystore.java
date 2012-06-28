package uk.co.stuforbes.sparktest.keystore;

import redis.clients.jedis.Jedis;

import com.google.inject.Inject;
import com.google.inject.name.Named;

public class RedisKeystore implements Keystore {

	private final Jedis jedis;

	@Inject
	public RedisKeystore(@Named("redis-keystore-host") final String host) {
		this.jedis = new Jedis(host);
	}

	public void save(final String key, final String json) {
		jedis.set(key, json);
	}

	public String retrieve(final String key) {
		return jedis.get(key);
	}

}
