package me.inao.discordbot.command;

import me.inao.discordbot.Main;
import me.inao.discordbot.ifaces.ICommand;
import me.inao.discordbot.util.MessageSender;
import org.javacord.api.entity.message.Message;

import java.awt.*;

public class ServerToken implements ICommand{
    @Override
    public void onCommand(Main instance, Message message, String[] args) {
        if(!instance.getPermissionable().hasPermission(message, this.getClass())){
            new MessageSender("No Permissions", instance.getConfig().getMessage("generic", "no_perms"), Color.RED, message.getChannel());
            return;
        }
        if(!(Boolean.parseBoolean(instance.getConfig().getServerProperty("enabled")))){
            System.out.println("here");
        }

    }

    @Override
    public String getUsage() {
        return null;
    }
}
