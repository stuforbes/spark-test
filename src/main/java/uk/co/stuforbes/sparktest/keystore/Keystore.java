package uk.co.stuforbes.sparktest.keystore;

public interface Keystore {

	void save(String key, String json);

	String retrieve(String key);
}
