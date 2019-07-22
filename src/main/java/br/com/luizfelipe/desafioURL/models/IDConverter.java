package br.com.luizfelipe.desafioURL.models;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;


public class IDConverter {
	
	Map<String, String> map = new HashMap<String, String>();

	public String encode(String longUrl) {
        String r = getRandomId();
        map.put(r, longUrl);
		return r;
	}

	public String decode(String shortUrl) {
		String id = shortUrl.substring(shortUrl.length() - 6);
        return map.get(id);
	}

	private String getRandomId() {
		char[] array = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray();
		int index = 0;
		StringBuffer sb = new StringBuffer();
		while (index < 6) {
			int random = (int) (Math.random() * (array.length - index));
			sb.append(array[random]);
			array[random] = array[array.length - 1 - index];
			index++;
		}
		return sb.toString();
	}

}
