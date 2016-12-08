package mk.klikniobrok.services.impl;

import mk.klikniobrok.services.SecurityService;
import org.springframework.stereotype.Service;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Arrays;
import java.util.Random;

/**
 * Created by andrejnaumovski on 12/8/16.
 */

@Service
public class SecurityServiceImpl implements SecurityService {
    @Override
    public String hashPassword(String original, String salt) {
        String hashToStore = null;
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest((original + salt).getBytes("UTF-8"));
            hashToStore = byteToBase64(hash) + "." + salt;
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return hashToStore;
    }

    @Override
    public String generateRandomSalt() {
        final Random randomGen = new SecureRandom();
        byte[] salt = new byte[16];
        randomGen.nextBytes(salt);
        return byteToBase64(salt);
    }

    @Override
    public byte[] base64ToByte(String str) {
        byte[] returnByteArray = null;
        try {
            BASE64Decoder decoder = new BASE64Decoder();
            returnByteArray = decoder.decodeBuffer(str);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return returnByteArray;
    }

    @Override
    public String byteToBase64(byte[] bt) {

        BASE64Encoder enDecoder = new BASE64Encoder();
        return enDecoder.encode(bt);
    }
}
