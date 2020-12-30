package me.inao.discordbot.server;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Packet {
    private String token = null;
    private String action = null;
    private String origin = null;
    private String channel = null;
    private String message = null;
    private String user = null;
    private String code = null;
}
