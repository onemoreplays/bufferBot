package me.inao.discordbot.event;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import me.inao.discordbot.Main;
import me.inao.discordbot.exception.NoSuchServerTextChannelException;
import me.inao.discordbot.ifaces.IListener;
import me.inao.discordbot.util.Logger;
import me.inao.discordbot.util.MessageSender;
import org.apache.logging.log4j.Level;
import org.javacord.api.event.server.member.ServerMemberJoinEvent;
import org.javacord.api.listener.server.member.ServerMemberJoinListener;

import java.awt.*;

@RequiredArgsConstructor
public class OnJoinEvent implements ServerMemberJoinListener, IListener {
    @Getter
    @Setter
    private final Main main;

    @Override
    public void onServerMemberJoin(ServerMemberJoinEvent e) {
        if(main.getConfig().isFeatureEnabled("joinMessage")){
            new MessageSender(e.getUser().getDiscriminatedName() + " has joined", main.getConfig().getFeatureData("joinMessage").replace("%_user_%", e.getUser().getDiscriminatedName()), Color.GREEN, e.getServer().getChannelsByName(main.getConfig().getFeatureChannel("joinMessage")).get(0).asServerTextChannel().orElseThrow(NoSuchServerTextChannelException::new));
        }
//        if(main.getConfig().isFeatureEnabled("captchaSystem")){
//            e.getUser().addRole(e.getServer().getRolesByName(main.getConfig().getFeatureData("captchaSystem").split(";")[1]).get(0));
//
//        }else{
//            e.getUser().addRole(e.getServer().getRolesByName(main.getConfig().getFeatureData("captchaSystem").split(";")[2]).get(0));
//        }
        new Logger(main, false, true, "Join", "User " + e.getUser().getDiscriminatedName() + " has joined", Level.INFO);
    }
}