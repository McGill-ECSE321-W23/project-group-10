package ca.mcgill.ecse321.parkinglotsystem.service.utilities;

import org.springframework.security.crypto.codec.Hex;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Contains helper methods to generate an authentication token.
 * 
 * Credit: https://intellitech.pro/tutorial-1-spring-security-authentication-using-token/
 */
public class TokenUtil {

	private static final String SECRET_KEY = "PLS_ECSE321_G10";
    private static final int VALIDITY_HOURS = 24;

	/**
	 * Generates an authentication token.
	 * @param email the email of a Person 
	 * @return the generated token
	 */
	public static String createToken(String email) {
		long expires = System.currentTimeMillis() + 1000 * 60 * 60 * VALIDITY_HOURS;
		return expires + ":" + computeSignature(email, expires);
	}

	/**
	 * Computes the signature to be included in an authentication token.
	 * @param email the email of a person
	 * @param expires the time of expiration of the token
	 * @return the computed signature
	 */
	private static String computeSignature(String email, long expires) {
		StringBuilder signatureBuilder = new StringBuilder();
		signatureBuilder.append(email).append(":");
		signatureBuilder.append(expires).append(":");
		signatureBuilder.append(email).append(":");
		signatureBuilder.append(TokenUtil.SECRET_KEY);

		MessageDigest digest;
		try {
			digest = MessageDigest.getInstance("MD5");
		} catch (NoSuchAlgorithmException e) {
			throw new IllegalStateException("No MD5 algorithm available!");
		}
		return new String(Hex.encode(digest.digest(signatureBuilder.toString().getBytes())));
	}
}