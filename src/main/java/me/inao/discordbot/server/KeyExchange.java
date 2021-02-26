package me.inao.discordbot.server;

import com.google.common.hash.Hashing;
import lombok.Getter;

import javax.crypto.Cipher;
import javax.crypto.KeyAgreement;
import javax.crypto.spec.SecretKeySpec;
import java.security.*;
import java.security.spec.X509EncodedKeySpec;
import java.time.LocalDateTime;
import java.util.Base64;

public class KeyExchange {
    @Getter
    private PublicKey pubKey;
    KeyAgreement keyAgreement;
    byte[] sharedSecret;
    private final LocalDateTime validUntil;
    private final String ALG = "AES";

    public KeyExchange(){
        this.validUntil =  LocalDateTime.now().plusMinutes(5);
        makeKeyExchangePair();
    }

    public void makeKeyExchangePair(){
        KeyPairGenerator generator = null;
        try{
            generator = KeyPairGenerator.getInstance("EC");
            generator.initialize(256);
            KeyPair pair = generator.generateKeyPair();
            this.pubKey = pair.getPublic();
            keyAgreement = KeyAgreement.getInstance("ECDH");
            keyAgreement.init(pair.getPrivate());
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public boolean isSafe(){
        if(LocalDateTime.now().isBefore(this.validUntil)){
            return true;
        }
        return false;
    }

    public void setReceiverPublicKey(byte[] publickey) {
        try {
            KeyFactory factory = KeyFactory.getInstance("EC");
            PublicKey key = factory.generatePublic(new X509EncodedKeySpec(publickey));
            keyAgreement.doPhase(key, true);
            sharedSecret = keyAgreement.generateSecret();
            System.out.println(new String(Base64.getEncoder().encode(generateKey().getEncoded())));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String encrypt(String msg) {
        try {
            Key key = generateKey();
            assert key != null;

            System.out.println(new String(Base64.getEncoder().encode(key.getEncoded())));
            Cipher c = Cipher.getInstance("AES");
            c.init(Cipher.ENCRYPT_MODE, key);
            byte[] encVal = c.doFinal(msg.getBytes());
            return new String(Base64.getEncoder().encode(encVal));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return msg;
    }
    private Key generateKey() {
        try{
            return new SecretKeySpec(Hashing.sha256().hashBytes(this.sharedSecret).asBytes(), ALG);
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
}
