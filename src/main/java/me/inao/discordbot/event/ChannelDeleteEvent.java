package me.inao.discordbot.event;

import lombok.RequiredArgsConstructor;
import me.inao.discordbot.Main;
import me.inao.discordbot.ifaces.IListener;
import org.javacord.api.event.channel.server.ServerChannelDeleteEvent;
import org.javacord.api.listener.channel.server.ServerChannelDeleteListener;

import java.sql.Connection;
import java.sql.PreparedStatement;

@RequiredArgsConstructor
public class ChannelDeleteEvent implements ServerChannelDeleteListener, IListener {
    private final Main main;
    @Override
    public void onServerChannelDelete(ServerChannelDeleteEvent e) {
        if(!main.getConfig().isFeatureEnabled("captchaSystem")){
            return;
        }
        if(e.getChannel().getName().matches("(captcha)-\\d+")){
            String[] split = e.getChannel().getName().split("-");
            Connection connection =  main.getSqlite().openConnection();
            try{
                PreparedStatement st = connection.prepareStatement("DELETE FROM captcha WHERE userid = ?");
                st.setString(1, split[1]);
                main.getSqlite().execute(st);
                st.close();
                connection.close();
            }catch (Exception exception){
                exception.printStackTrace();
            }
            main.getApi().getUserById(split[1]).join().removeRole(e.getServer().getRolesByName(main.getConfig().getFeatureData("captchaSystem").split(";")[1]).get(0));
            main.getApi().getUserById(split[1]).join().addRole(e.getServer().getRolesByName(main.getConfig().getFeatureData("captchaSystem").split(";")[2]).get(0));
        }
    }
}
