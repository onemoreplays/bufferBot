package me.inao.discordbot.event;

import lombok.RequiredArgsConstructor;
import me.inao.discordbot.Main;
import me.inao.discordbot.util.ExceptionCatcher;
import me.inao.discordbot.util.MessageSender;
import org.javacord.api.event.server.member.ServerMemberLeaveEvent;
import org.javacord.api.listener.server.member.ServerMemberLeaveListener;

import java.awt.*;
import java.sql.PreparedStatement;

@RequiredArgsConstructor
public class OnLeaveEvent implements ServerMemberLeaveListener {
    private final Main main;
    @Override
    public void onServerMemberLeave(ServerMemberLeaveEvent e) {
        e.getUser().getRoles(e.getServer()).iterator().forEachRemaining(role -> {
            if(role.getName().equals(main.getConfig().getFeatureData("captchaSystem").split(";")[1])){
                e.getServer().getChannelsByName("captcha-" + e.getUser().getIdAsString()).get(0).delete();
                try{
                    PreparedStatement stmnt = main.getSqlite().openConnection().prepareStatement("DELETE FROM captcha WHERE userid = ?");
                    stmnt.setString(1, e.getUser().getIdAsString());
                    main.getSqlite().execute(stmnt);
                    stmnt.getConnection().close();
                    stmnt.close();
                }catch (Exception exd){
                    new ExceptionCatcher(exd);
                }
            }
        });
        if(main.getConfig().isFeatureEnabled("leaveMessage")){
            new MessageSender(e.getUser().getDiscriminatedName() + " has left us :(", main.getConfig().getMessage("leaveMessage", "success"), Color.RED, e.getServer().getChannelsByName(main.getConfig().getFeatureData("leaveSystem")).get(0).asServerTextChannel().get());
        }
    }
}
