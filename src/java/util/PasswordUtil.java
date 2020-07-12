package util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import javax.xml.bind.DatatypeConverter;

public class PasswordUtil {
    public String encryptPassword (String password) throws NoSuchAlgorithmException {
        MessageDigest msgDigest = MessageDigest.getInstance("MD5");
        msgDigest.update(password.getBytes());

        byte[] digest = msgDigest.digest();

        String encryptedPassword = DatatypeConverter.printHexBinary(digest);
        
        return encryptedPassword.toLowerCase();
    }
}
