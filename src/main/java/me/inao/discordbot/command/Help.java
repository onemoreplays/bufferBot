package me.inao.discordbot.command;

import me.inao.discordbot.Main;
import me.inao.discordbot.ifaces.ICommand;
import me.inao.discordbot.util.MessageSender;
import org.javacord.api.entity.message.Message;
import org.javacord.api.entity.message.MessageBuilder;
import org.javacord.api.entity.message.embed.EmbedBuilder;

import java.awt.*;

public class Help implements ICommand{

    @Override
    public void onCommand(Main instance, Message message, String[] args) {
        if(!instance.getPermissionable().hasPermission(message, this.getClass())){
            new MessageSender("No Permissions", instance.getConfig().getMessage("generic", "no_perms"), Color.RED, message.getChannel());
            return;
        }

        EmbedBuilder builder = new EmbedBuilder();
        builder.setDescription("Helping everyone is my pleasure, you know :eyes:").setTitle("Help");
        builder.setFooter("Prefix for commands is " + instance.getConfig().getPrefix());

        instance.getLoader().getLoadedCommands().entrySet().iterator().forEachRemaining(st->{
            if(instance.getConfig().isCommandEnabled(st.getKey())){
                if(st.getValue().getUsage() != null){
                    builder.addField(st.getKey(), st.getKey() + " " + st.getValue().getUsage());
                }else{
                    builder.addField(st.getKey(), "No parameters");
                }
            }
        });
        new MessageBuilder().setEmbed(builder).send(message.getChannel());
    }

    @Override
    public String getUsage() {
        return null;
    }
}
