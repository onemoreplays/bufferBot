package me.inao.discordbot.event;

import lombok.RequiredArgsConstructor;
import me.inao.discordbot.Main;
import me.inao.discordbot.exception.NoSuchServerTextChannelException;
import me.inao.discordbot.ifaces.IListener;
import me.inao.discordbot.util.Logger;
import me.inao.discordbot.util.MessageSender;
import org.apache.logging.log4j.Level;
import org.javacord.api.event.server.member.ServerMemberLeaveEvent;
import org.javacord.api.listener.server.member.ServerMemberLeaveListener;

import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;

@RequiredArgsConstructor
public class OnLeaveEvent implements ServerMemberLeaveListener, IListener {
    private final Main main;
    @Override
    public void onServerMemberLeave(ServerMemberLeaveEvent e) {
        if(main.getConfig().isFeatureEnabled("captchaSystem")){
            e.getServer().getRoles(e.getUser()).iterator().forEachRemaining(role -> {
                if(role.getName().equals(main.getConfig().getFeatureData(main.getConfig().getFeatureData("captchaSystem")).split(";")[1])){
                    Connection connection =  main.getSqlite().openConnection();
                    try{
                        PreparedStatement st = connection.prepareStatement("DELETE FROM captcha WHERE userid = ?");
                        st.setString(1, e.getUser().getIdAsString());
                        main.getSqlite().execute(st);
                        st.close();
                        connection.close();
                    }catch (Exception exception){
                        exception.printStackTrace();
                    }
                    e.getServer().getChannelsByName("captcha-"+e.getUser().getIdAsString()).get(0).delete();
                }
            });
        }
        if(main.getConfig().isFeatureEnabled("leaveMessage")){
            new MessageSender(e.getUser().getDiscriminatedName() + " has left us :(",
                    main.getConfig().getMessage("leave", "success").replace("%_user_%", e.getUser().getDiscriminatedName()),
                    Color.RED,
                    e.getServer().getChannelsByName(main.getConfig().getFeatureData("leaveMessage")).get(0).asServerTextChannel().orElseThrow(NoSuchServerTextChannelException::new)
            );
            new Logger(main, false, true, "Leave", "User " + e.getUser().getDiscriminatedName() + " has left", Level.INFO);
        }
    }
}
