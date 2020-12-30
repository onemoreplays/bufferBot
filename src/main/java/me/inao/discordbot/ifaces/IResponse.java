package me.inao.discordbot.ifaces;

import me.inao.discordbot.server.Packet;
import org.javacord.api.DiscordApi;

public interface IResponse {
    void onReceive(DiscordApi api, Packet packet);
}
