package com.ddd.books.in.spring.configuration.security;

import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.CharacterCodingException;
import java.nio.charset.Charset;
import java.nio.charset.CharsetEncoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class CryptoUtils {
    private static final Charset CHARSET = Charset.forName("UTF-8");
    private static final char[] HEX_SYMBOLS = {
            '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };

    public static String encodePassword(final CharSequence password) {
        try {
            final MessageDigest digest = MessageDigest.getInstance("SHA-512");
            final byte[] encoded = byteEncode(password);
            final byte[] digested = digest.digest(encoded);
            final char[] chars = charEncode(digested);
            return new String(chars);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Couldn't load instance");
        } catch (CharacterCodingException e) {
            throw new RuntimeException("Couldn't byteEncode password");
        }
    }

    private static byte[] byteEncode(final CharSequence sequence) throws CharacterCodingException {
        final CharsetEncoder encoder = CHARSET.newEncoder();
        final ByteBuffer bytes = encoder.encode(CharBuffer.wrap(sequence));
        final byte[] bytesCopy = new byte[bytes.limit()];
        System.arraycopy(bytes.array(), 0, bytesCopy, 0, bytesCopy.length);

        return bytesCopy;
    }

    private static char[] charEncode(final byte[] bytes) {
        final int length = bytes.length;
        char[] result = new char[2 * length];

        int i = 0;
        for (byte b : bytes) {
            result[i++] = HEX_SYMBOLS[(0xF0 & b) >>> 4];
            result[i++] = HEX_SYMBOLS[(0x0F & b)];
        }

        return result;
    }
}
