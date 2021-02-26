package me.inao.discordbot.ifaces;

import org.javacord.api.DiscordApi;

public interface IParameter {
    void onParse(DiscordApi api, String value);
    String[] getIdentifiers();
}
