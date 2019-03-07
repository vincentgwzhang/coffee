package tina.coffee.system.security.encoder;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class DigestUtils {

	static MessageDigest getDigest(String algorithm) {
		try {
			return MessageDigest.getInstance(algorithm);
		} catch (NoSuchAlgorithmException e) {
			throw new RuntimeException(e.getMessage());
		}
	}

	private static MessageDigest getMd5Digest() {
		return getDigest("MD5");
	}

	private static MessageDigest getShaDigest() {
		return getDigest("SHA");
	}

	private static MessageDigest getSha256Digest() {
		return getDigest("SHA-256");
	}

	private static MessageDigest getSha384Digest() {
		return getDigest("SHA-384");
	}

	private static MessageDigest getSha512Digest() {
		return getDigest("SHA-512");
	}

	public static byte[] encodeMD5(byte[] data) {
		return getMd5Digest().digest(data);
	}

	public static String encodeMD5Hex(byte[] data) {
		return Hex.byte2HexStr(encodeMD5(data));
	}

	public static byte[] encodeSHA(byte[] data) {
		return getShaDigest().digest(data);
	}

	public static String encodeSHAHex(byte[] data) {
		return Hex.byte2HexStr(getShaDigest().digest(data));
	}

	public static byte[] encodeSHA256(byte[] data) {
		return getSha256Digest().digest(data);
	}

	public static String encodeSHA256Hex(byte[] data) {
		return Hex.byte2HexStr(encodeSHA256(data));
	}

	public static byte[] encodeSHA384(byte[] data) {
		return getSha384Digest().digest(data);
	}

	public static String encodeSHA384Hex(byte[] data) {
		return Hex.byte2HexStr(encodeSHA384(data));
	}

	public static byte[] encodeSHA512(byte[] data) {
		return getSha512Digest().digest(data);
	}

	public static String encodeSHA512Hex(byte[] data) {
		return Hex.byte2HexStr(encodeSHA512(data));
	}

	public static void main(String[] args) {
		String time = "password";
		String result = encodeSHA512Hex(time.getBytes());
		System.out.println(time);
		System.out.println(result);
	}
}
