package me.inao.discordbot.server.actions;

import me.inao.discordbot.ifaces.IAction;
import org.javacord.api.DiscordApi;
import org.javacord.api.entity.channel.Channel;

public class Message implements IAction {
    @Override
    public void onReceive(DiscordApi api, String message, String origin, String channel) {
        api.getChannelById(channel).flatMap(Channel::asServerTextChannel).ifPresent(txtChannel -> {
            txtChannel.sendMessage("Message from " + origin + "\nMessage: " + message);
        });
    }
}
