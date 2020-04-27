package me.inao.discordbot.event;

import lombok.Getter;
import lombok.Setter;
import me.inao.discordbot.Main;
import me.inao.discordbot.buffer.JoiningBuffer;
import me.inao.discordbot.exception.NoSuchServerTextChannelException;
import me.inao.discordbot.objects.Captcha;
import me.inao.discordbot.util.MessageSender;
import org.javacord.api.event.server.member.ServerMemberJoinEvent;
import org.javacord.api.listener.server.member.ServerMemberJoinListener;
import java.awt.*;
import java.util.Timer;

public class OnJoinEvent implements ServerMemberJoinListener {
    @Getter
    @Setter
    private int joins = 0;
    private final Main main;
    private boolean running = false;
    public OnJoinEvent(Main main){
        this.main = main;
    }


    @Override
    public void onServerMemberJoin(ServerMemberJoinEvent e) {
        joins++;
        if(!(running)){
            running = true;
            JoiningBuffer buffer = new JoiningBuffer(this);
            Timer timer = new Timer();
            timer.schedule(buffer, 30000);
        }
        if(main.getConfig().isFeatureEnabled("joinMessage")){
            new MessageSender(e.getUser().getDiscriminatedName() + " has joined", main.getConfig().getMessage("welcome", "success").replace("%_user_%", e.getUser().getDiscriminatedName()), Color.GREEN, e.getServer().getChannelsByName(main.getConfig().getFeatureData("joinMessage")).get(0).asServerTextChannel().orElseThrow(NoSuchServerTextChannelException::new));
        }
        if(joins > 5 && main.getConfig().isFeatureEnabled("captchaSystem")){
            e.getUser().addRole(e.getServer().getRolesByName(main.getConfig().getFeatureData("captchaSystem").split(";")[1]).get(0));
            new Captcha().gen(main, e.getUser().getIdAsString(), e.getServer(), e.getUser());
        }else{
            e.getUser().addRole(e.getServer().getRolesByName(main.getConfig().getFeatureData("captchaSystem").split(";")[2]).get(0));
        }
    }
}