package me.inao.discordbot.command;

import me.inao.discordbot.Main;
import me.inao.discordbot.ifaces.ICommand;
import me.inao.discordbot.ifaces.Permissionable;
import me.inao.discordbot.objects.Captcha;
import me.inao.discordbot.util.MessageSender;
import me.inao.discordbot.util.PermissionCheck;
import org.javacord.api.entity.message.Message;

import java.awt.*;
import java.security.SecureRandom;

public class Ping extends Permissionable implements ICommand {
    @Override
    public void onCommand(Main instance, Message message, String[] args) {
        if(!hasPermission(instance, message, this.getClass())){
            new MessageSender("No Permissions", instance.getConfig().getMessage("generic", "no_perms"), Color.RED, message.getChannel());
            return;
        }
        new Captcha().gen(instance, message.getAuthor().getIdAsString(), message.getServer().get(), message.getUserAuthor().get());
    }

    @Override
    public String getUsage() {
        return null;
    }
}
