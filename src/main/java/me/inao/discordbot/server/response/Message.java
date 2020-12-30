package me.inao.discordbot.server.response;

import me.inao.discordbot.ifaces.IResponse;
import me.inao.discordbot.server.Packet;
import me.inao.discordbot.server.request.CaptchaRequest;
import org.javacord.api.DiscordApi;

public class Message implements IResponse {

    @Override
    public void onReceive(DiscordApi api, Packet packet) {
        //api.getChannelById(packet.getChannel()).flatMap(Channel::asServerTextChannel).ifPresent(txtChannel -> txtChannel.sendMessage("Message from " + packet.getOrigin() + "\nMessage: " + packet.getMessage()));
        new CaptchaRequest().onRequest("test", packet);
    }
}
