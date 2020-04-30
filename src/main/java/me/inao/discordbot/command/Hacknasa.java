package me.inao.discordbot.command;

import me.inao.discordbot.Main;
import me.inao.discordbot.ifaces.ICommand;
import me.inao.discordbot.ifaces.Permissionable;
import me.inao.discordbot.util.MessageSender;
import org.javacord.api.entity.message.Message;

import java.awt.*;

public class Hacknasa extends Permissionable implements ICommand {
    @Override
    public void onCommand(Main instance, Message message, String[] args) {
        if(!hasPermission(instance, message, this.getClass())){
            return;
        }
        new MessageSender("Hacking..", instance.getConfig().getMessage(this.getClass().getSimpleName(), "success"), Color.RED, message.getChannel());
    }

    @Override
    public String getUsage() {
        return null;
    }
}
