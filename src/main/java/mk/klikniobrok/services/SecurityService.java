package mk.klikniobrok.services;

/**
 * Created by andrejnaumovski on 12/8/16.
 */
public interface SecurityService {
    String hashPassword(String original, String salt);
    String generateRandomSalt();
    String byteToBase64(byte[] bt);
    byte[] base64ToByte(String str);
}
