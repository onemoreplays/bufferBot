package me.inao.discordbot.ifaces;

import me.inao.discordbot.server.Packet;

public interface IRequest {
    void onRequest(String ip, Packet packet);
    int getType();
}
