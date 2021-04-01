package me.inao.discordbot.server;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
public class Session {
    @Setter
    private Date validity;

    @Setter
    private String token;

    @Setter
    private byte[] secret;

    private final KeyExchange keyExchange = new KeyExchange();
}