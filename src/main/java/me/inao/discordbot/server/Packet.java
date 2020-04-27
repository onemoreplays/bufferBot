package me.inao.discordbot.server;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class Packet {
    private final String token;
    private final String action;
    private final String origin;
    private final String channel;
    private final String message;
}
