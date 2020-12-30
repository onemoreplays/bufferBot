package me.inao.discordbot.event;

import lombok.RequiredArgsConstructor;
import me.inao.discordbot.Main;
import me.inao.discordbot.ifaces.IListener;
import org.javacord.api.event.channel.server.ServerChannelDeleteEvent;
import org.javacord.api.listener.channel.server.ServerChannelDeleteListener;

@RequiredArgsConstructor
public class ChannelDeleteEvent implements ServerChannelDeleteListener, IListener {
    private final Main main;
    @Override
    public void onServerChannelDelete(ServerChannelDeleteEvent e) {
        if(!main.getConfig().isFeatureEnabled("captchaSystem")){
            return;
        }
        //todo: captcha deletion.
    }
}
