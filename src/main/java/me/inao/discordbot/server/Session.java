package me.inao.discordbot.server;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

public class Session {
    public Session(){
        this.keyExchange = new KeyExchange();
    }
    @Getter
    @Setter
    private String identifier;

    @Setter
    @Getter
    private String key;

    @Setter
    @Getter
    private Date validity;

    @Getter
    @Setter
    private String token;

    @Getter
    private byte[] sharedSecret;

    @Getter
    private final KeyExchange keyExchange;
}