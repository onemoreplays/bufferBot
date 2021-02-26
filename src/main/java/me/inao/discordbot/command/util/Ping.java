package me.inao.discordbot.command.util;

import me.inao.discordbot.Main;
import me.inao.discordbot.ifaces.ICommand;
import me.inao.discordbot.util.MessageSender;
import org.javacord.api.entity.message.Message;

import java.awt.*;

public class Ping implements ICommand {
    @Override
    public void onCommand(Main instance, Message message, String[] args) {
        if(!instance.getPermissionable().hasPermission(message, this.getClass())){
            new MessageSender("No Permissions", instance.getConfig().getMessage("generic", "no_perms"), Color.RED, message.getChannel());
            return;
        }
        new MessageSender("pong", "pong", Color.RED, message.getChannel());
    }

    @Override
    public String getUsage() {
        return null;
    }
}
