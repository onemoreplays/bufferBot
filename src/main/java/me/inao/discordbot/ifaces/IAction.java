package me.inao.discordbot.ifaces;

import org.javacord.api.DiscordApi;

public interface IAction {
    void onReceive(DiscordApi api, String message, String origin, String channel);
}
