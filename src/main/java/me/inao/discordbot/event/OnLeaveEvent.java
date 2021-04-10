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

@RequiredArgsConstructor
public class OnLeaveEvent implements ServerMemberLeaveListener, IListener {
    private final Main main;
    @Override
    public void onServerMemberLeave(ServerMemberLeaveEvent e) {
        if(main.getConfig().isFeatureEnabled("leaveMessage")){
            new MessageSender(e.getUser().getDiscriminatedName() + " has left",
                    main.getConfig().getMessage("leave", "success").replace("%_user_%", e.getUser().getDiscriminatedName()),
                    Color.RED,
                    e.getServer().getChannelsByName(main.getConfig().getFeatureChannel("leaveMessage")).get(0).asServerTextChannel().orElseThrow(NoSuchServerTextChannelException::new)
            );
            new Logger(main, false, true, "Leave", "User " + e.getUser().getDiscriminatedName() + " has left", Level.INFO);
        }
    }
}
