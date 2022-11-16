/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package metodos;

import java.security.MessageDigest;
import java.util.Arrays;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import org.apache.commons.codec.binary.Base64;

/**
 *
 * @author Novoa
 */
public class AuthServicio {
 
    protected static final String LLAVE = "9PQab3ELuZ8EK7fC_g9uDM-tZSA3ARjTqgYBTxsh36fcjp1tTtpCVW-VmbYWTAhvW1_mZbynjN-pz74ZLhd1PBnuc5iEpPgA";
    
    private SecretKeySpec crearLlave(String llave) throws Exception {
        byte[] cadena = llave.getBytes("UTF-8");
        MessageDigest md = MessageDigest.getInstance("SHA-1");
        cadena = md.digest(cadena);
        cadena = Arrays.copyOf(cadena, 16);
        SecretKeySpec secretKeySpec = new SecretKeySpec(cadena, "AES");
        return secretKeySpec;
    }
    // 12345
    // fdfdf445f4s5f4sf54sf4dsf4ds45f64df
    public String encriptar(String texto) throws Exception {
        SecretKeySpec secretKeySpec = this.crearLlave(LLAVE);
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(1, secretKeySpec);
        byte[] cadena = texto.getBytes("UTF-8");
        byte[] incriptado = cipher.doFinal(cadena);
        return Base64.encodeBase64String(incriptado);
    }
    
    public String desencriptar(String texto) throws Exception {
        SecretKeySpec secretKeySpec = this.crearLlave(LLAVE);
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(2, secretKeySpec);
        byte[] cadena = Base64.decodeBase64(texto);
        byte[] desencriptado = cipher.doFinal(cadena);
        return new String(desencriptado);
    }
}
