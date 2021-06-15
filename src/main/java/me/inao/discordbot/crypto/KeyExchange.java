package me.inao.discordbot.crypto;

import lombok.Getter;
import lombok.Setter;
import org.bouncycastle.crypto.AsymmetricCipherKeyPair;
import org.bouncycastle.crypto.KeyGenerationParameters;
import org.bouncycastle.pqc.crypto.newhope.NHAgreement;
import org.bouncycastle.pqc.crypto.newhope.NHKeyPairGenerator;
import org.bouncycastle.pqc.crypto.newhope.NHPublicKeyParameters;
import org.bouncycastle.util.encoders.Base64;

import java.security.SecureRandom;

public class KeyExchange {
    @Getter
    private AsymmetricCipherKeyPair exchangePair;

    @Getter
    @Setter
    private String clientEncodedPubKey = null;

    @Getter
    private byte[] sharedSecret = null;

    public void initKeys() {
        NHKeyPairGenerator nhKeyPairGenerator = new NHKeyPairGenerator();
        nhKeyPairGenerator.init(new KeyGenerationParameters(new SecureRandom(), 2048));
        exchangePair = nhKeyPairGenerator.generateKeyPair();
    }

    public void createAgreement() {
        NHPublicKeyParameters parameters = new NHPublicKeyParameters(Base64.decode(clientEncodedPubKey));
        NHAgreement nhAgreement = new NHAgreement();
        nhAgreement.init(exchangePair.getPrivate());
        sharedSecret = nhAgreement.calculateAgreement(parameters);
        exchangePair = null;
        clientEncodedPubKey = null;
    }

    public String getPublicKeyEncodedString() {
        return new String(Base64.encode(((NHPublicKeyParameters) exchangePair.getPublic()).getPubData()));
    }

    public byte[] getPublicKeyEncodedBytes() {
        return Base64.encode(((NHPublicKeyParameters) exchangePair.getPublic()).getPubData());
    }

    public NHPublicKeyParameters getNhPublicKeyParametersFromString(String base64) {
        return new NHPublicKeyParameters(Base64.decode(base64));
    }

    public String encodeBase64(byte[] raw) {
        return new String(Base64.encode(raw));
    }
}
