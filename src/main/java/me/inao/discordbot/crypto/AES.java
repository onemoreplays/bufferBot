package me.inao.discordbot.crypto;

import lombok.AllArgsConstructor;
import me.inao.discordbot.Main;
import me.inao.discordbot.util.ExceptionCatcher;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.SecureRandom;
import java.util.Base64;

@AllArgsConstructor
public class AES {
    private final Main instance;
    public String getEncrypted(String plain){
        try{
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            SecretKeySpec spec = new SecretKeySpec(Base64.getDecoder().decode(instance.getConfig().getServerProperty("aesKey")), "AES");
            IvParameterSpec ivpsp = new IvParameterSpec(Base64.getDecoder().decode(instance.getConfig().getServerProperty("aesIv").getBytes()));
            cipher.init(Cipher.ENCRYPT_MODE, spec, ivpsp);
            return Base64.getEncoder().encodeToString(cipher.doFinal(plain.getBytes()));
        }catch (Exception e){
            new ExceptionCatcher(e);
        }
        return null;
    }

    public String getDecrypted(String cipherT){
        try{
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            SecretKeySpec spec = new SecretKeySpec(Base64.getDecoder().decode(instance.getConfig().getServerProperty("aesKey")), "AES");
            IvParameterSpec ivpsp = new IvParameterSpec(Base64.getDecoder().decode(instance.getConfig().getServerProperty("aesIv").getBytes()));
            cipher.init(Cipher.DECRYPT_MODE, spec, ivpsp);
            return new String(cipher.doFinal(Base64.getDecoder().decode(cipherT.getBytes())));
        }catch (Exception e){
            new ExceptionCatcher(e);
        }
        return null;
    }
    public String getIv(){
        byte[] IV = new byte[16];
        SecureRandom random = new SecureRandom();
        random.nextBytes(IV);
        return Base64.getEncoder().encodeToString(IV);
    }

    public String getKey(){
        try{
            KeyGenerator generator = KeyGenerator.getInstance("AES");
            generator.init(256);
            SecretKey key = generator.generateKey();
            return Base64.getEncoder().encodeToString(key.getEncoded());
        }catch (Exception e){
            new ExceptionCatcher(e);
        }
        return null;
    }
}
