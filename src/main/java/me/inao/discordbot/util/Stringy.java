package me.inao.discordbot.util;

import java.util.UUID;

public class Stringy {
    public static String getRandomIdentifier(){
        return UUID.randomUUID().toString().replaceAll("-", "");
    }
}
