package me.inao.discordbot.server;

import lombok.Getter;
import org.bouncycastle.jce.ECNamedCurveTable;
import org.bouncycastle.jce.spec.ECParameterSpec;
import org.bouncycastle.jce.spec.ECPrivateKeySpec;
import org.bouncycastle.jce.spec.ECPublicKeySpec;

import javax.crypto.KeyAgreement;
import java.math.BigInteger;
import java.security.*;
import java.security.spec.ECGenParameterSpec;
import java.util.Base64;

public class KeyExchange {
    @Getter
    KeyPair pair;
    public void initKeys(){
        try{
            KeyPairGenerator generator = KeyPairGenerator.getInstance("ECDH", "BC");
            generator.initialize(new ECGenParameterSpec("prime256v1"), new SecureRandom());
            pair = generator.generateKeyPair();
        }catch(Exception e){
            pair = null;
            e.printStackTrace();
        }
    }

    public PublicKey getPubKeyFromBytes(byte[] raw){
        ECParameterSpec paramsSpec = ECNamedCurveTable.getParameterSpec("prime256v1");
        ECPublicKeySpec pubKeySpec = new ECPublicKeySpec(paramsSpec.getCurve().decodePoint(raw), paramsSpec);
        try{
            KeyFactory factory = KeyFactory.getInstance("ECDH", "BC");
            return factory.generatePublic(pubKeySpec);
        }catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }

    public PrivateKey getPrivKeyFromBytes(byte[] raw){
        ECParameterSpec paramsSpec = ECNamedCurveTable.getParameterSpec("prime256v1");
        ECPrivateKeySpec privKeySpec = new ECPrivateKeySpec(new BigInteger(raw), paramsSpec);
        try{
            KeyFactory factory = KeyFactory.getInstance("ECDH", "BC");
            return factory.generatePrivate(privKeySpec);
        }catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }

    public byte[] calculateKey(byte[] privKey, byte[] pubKey){
        try{
            KeyAgreement secretAgreement = KeyAgreement.getInstance("ECDH", "BC");
            secretAgreement.init(getPrivKeyFromBytes(privKey));
            secretAgreement.doPhase(getPubKeyFromBytes(pubKey), true);
            return secretAgreement.generateSecret();
        }catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }

    public byte[] calculateKey(byte[] pubKey){
        try{
            KeyAgreement secretAgreement = KeyAgreement.getInstance("ECDH", "BC");
            secretAgreement.init(pair.getPrivate());
            secretAgreement.doPhase(getPubKeyFromBytes(pubKey), true);
            return secretAgreement.generateSecret();
        }catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }

    public String encodeBase64(byte[] raw){
        return new String(Base64.getEncoder().encode(raw));
    }
}
