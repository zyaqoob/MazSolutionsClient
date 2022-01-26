/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package crypto;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

/**
 *
 * @author Miguel Sanchez, Zeeshan Yaqoob, Aitor Ruiz de Gauna
 */
public class Crypto {

    public static String cifrar(String mensaje) {
        PublicKey publicKey = null;
        byte[] key = null;
        KeyFactory keyFactory;
        byte[] encodedMessage = null;
        try {
            InputStream is = Crypto.class.getResourceAsStream("PublicKey.key");
            byte[] encKey = toByteArray(is);
            is.close();
            /*byte[] fileContent = new byte[is.available()];
            is.read(fileContent, 0, is.available());
            key = fileContent;*/
            keyFactory = KeyFactory.getInstance("RSA");
            X509EncodedKeySpec spec = new X509EncodedKeySpec(encKey);
            publicKey = keyFactory.generatePublic(spec);
            Cipher cipherRSA = Cipher.getInstance("RSA/ECB/PKCS1Padding");
            cipherRSA.init(Cipher.ENCRYPT_MODE, publicKey);
            encodedMessage = cipherRSA.doFinal(mensaje.getBytes());

            //fileWriter("mensajecifrado.txt", encodedMessage);
        } catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeySpecException | InvalidKeyException | IllegalBlockSizeException | BadPaddingException | IOException ex) {
            Logger.getLogger(Crypto.class.getName()).log(Level.SEVERE, null, ex);
        }
        //We use the method covertStringToHex to get the hex value of the String
        return hexadecimal(encodedMessage);
    }

    public static String descifrar(String password) {
        //password=new String(hexStringToByteArray(password));
        KeyFactory factoriaRSA;
        String path = Crypto.class.getResource("PrivateKey.key").getPath();
        PrivateKey privateKey;
        Cipher cipher;
        String desc = null;
        byte[] key = fileReader(path);
        try {
            PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(key);
            factoriaRSA = KeyFactory.getInstance("RSA");
            privateKey = factoriaRSA.generatePrivate(spec);
            cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
            // Iniciamos el Cipher en ENCRYPT_MODE y le pasamos la clave privada
            //cipher.init(Cipher.ENCRYPT_MODE, privateKey);
            //IvParameterSpec ivParam = new IvParameterSpec(new byte[16]);	
            // Iniciamos el Cipher en ENCRYPT_MODE y le pasamos la clave privada y el ivParam
            cipher.init(Cipher.DECRYPT_MODE, privateKey);
            //Le decimos que descifre
            byte[] decodedMessage = cipher.doFinal(hexStringToByteArray(password));
            // Texto descifrado
            desc = new String(decodedMessage);
            //System.out.println(desc);
        } catch (NoSuchAlgorithmException | InvalidKeySpecException | NoSuchPaddingException | InvalidKeyException | IllegalBlockSizeException | BadPaddingException ex) {
            Logger.getLogger(Crypto.class.getName()).log(Level.SEVERE, null, ex);
        }
        return desc;
    }

    /**
     * Retorna el contenido de un fichero
     *
     * @param path Path del fichero
     * @return El texto del fichero
     */
    public static byte[] fileReader(String path) {
        byte ret[] = null;

        try {
            InputStream is = Crypto.class.getResourceAsStream("PublicKey.key");
            byte[] fileContent = new byte[is.available()];
            is.read(fileContent, 0, is.available());
            ret = fileContent;

        } catch (IOException e) {
        }
        return ret;
    }

    public static String hashPassword(String password) {
        MessageDigest messageDigest;
        String base64 = null;
        try {
            messageDigest = MessageDigest.getInstance("SHA");
            messageDigest.update(password.getBytes("UTF-8"));
            password = new String(messageDigest.digest());
            base64 = Base64.getEncoder().encodeToString(password.getBytes());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return base64;
    }

    private static String convertStringToHex(String password) {
        StringBuilder hex = new StringBuilder();
        for (int i = 0; i < password.length(); i++) {
            int decimal = (int) i;
            hex.append(Integer.toHexString(decimal));
        }
        return hex.toString();
    }

    static String hexadecimal(byte[] resumen) {
        String hex = "";
        for (int i = 0; i < resumen.length; i++) {
            String h = Integer.toHexString(resumen[i] & 0xFF);
            if (h.length() == 1) {
                hex += "0";
            }
            hex += h;
        }
        return hex.toUpperCase();
    }

    public static byte[] hexStringToByteArray(String password) {
        int len = password.length();
        byte[] mensajeByte = new byte[len / 2];
        for (int i = 0; i < len; i += 2) {
            mensajeByte[i / 2] = (byte) ((Character.digit(password.charAt(i), 16) << 4)
                    + Character.digit(password.charAt(i + 1), 16));
        }
        return mensajeByte;
    }

    public static String generatePassword() {
        String password;
        String charLow[] = {"a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z"};
        String charUpper[] = {"A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z"};
        String number[] = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9"};
        password = charLow[(int) (Math.random() * charLow.length)] + charUpper[(int) (Math.random() * charUpper.length)] + number[(int) (Math.random() * 9)] + charLow[(int) (Math.random() * charLow.length)] + charUpper[(int) (Math.random() * charUpper.length)] + number[(int) (Math.random() * 9)];
        return password;
    }

    public static byte[] toByteArray(InputStream in) throws IOException {
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int len;
        // read bytes from the input stream and store them in buffer
        while ((len = in.read(buffer)) != -1) {
            // write bytes from the buffer into output stream
            os.write(buffer, 0, len);
        }
        return os.toByteArray();
    }
}
