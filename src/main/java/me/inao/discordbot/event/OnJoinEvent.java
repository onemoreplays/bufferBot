package me.inao.discordbot.event;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import me.inao.discordbot.Main;
import me.inao.discordbot.exception.NoSuchServerTextChannelException;
import me.inao.discordbot.ifaces.IListener;
import me.inao.discordbot.objects.Captcha;
import me.inao.discordbot.util.Logger;
import me.inao.discordbot.util.MessageSender;
import org.apache.logging.log4j.Level;
import org.javacord.api.event.server.member.ServerMemberJoinEvent;
import org.javacord.api.listener.server.member.ServerMemberJoinListener;

import java.awt.*;
import java.util.Timer;
import java.util.TimerTask;

@RequiredArgsConstructor
public class OnJoinEvent implements ServerMemberJoinListener, IListener {
    @Getter
    @Setter
    private int joins = 0;
    private final Main main;
    private boolean cooldown = false;

    @Override
    public void onServerMemberJoin(ServerMemberJoinEvent e) {
        joins++;
        if(!(cooldown)){
            cooldown = true;
            Timer timer = new Timer();
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    cooldown = false;
                    joins = 0;
                }
            }, 30000);
        }
        if(main.getConfig().isFeatureEnabled("joinMessage")){
            new MessageSender(e.getUser().getDiscriminatedName() + " has joined", main.getConfig().getMessage("welcome", "success").replace("%_user_%", e.getUser().getDiscriminatedName()), Color.GREEN, e.getServer().getChannelsByName(main.getConfig().getFeatureData("joinMessage")).get(0).asServerTextChannel().orElseThrow(NoSuchServerTextChannelException::new));
        }
        if((cooldown && joins < 5) && main.getConfig().isFeatureEnabled("captchaSystem")){
            e.getUser().addRole(e.getServer().getRolesByName(main.getConfig().getFeatureData("captchaSystem").split(";")[1]).get(0));
            new Captcha().gen(main, e.getUser().getIdAsString(), e.getServer(), e.getUser());
        }else{
            e.getUser().addRole(e.getServer().getRolesByName(main.getConfig().getFeatureData("captchaSystem").split(";")[2]).get(0));
        }
        new Logger(main, false, true, "Join", "User " + e.getUser().getDiscriminatedName() + " has joined", Level.INFO);
    }
}