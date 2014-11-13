package edu.sjsu.cmpe.cache.client;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class ConsistentHash {

	public static int getHash(String hashingAlgo, Long key, int caches) throws NoSuchAlgorithmException {
		
		MessageDigest md = MessageDigest.getInstance(hashingAlgo);
		
		md.update(key.byteValue());

		byte[] digest = md.digest();

		BigInteger iDigest = new BigInteger(1, digest);

		return iDigest.mod(BigInteger.valueOf(caches)).intValue();
	}
}
