package me.inao.discordbot.command;

import me.inao.discordbot.Main;
import me.inao.discordbot.exception.NoSuchServerException;
import me.inao.discordbot.exception.NoSuchServerTextChannelException;
import me.inao.discordbot.ifaces.ICommand;
import me.inao.discordbot.ifaces.Permissionable;
import me.inao.discordbot.util.Logger;
import me.inao.discordbot.util.MessageSender;
import org.apache.logging.log4j.Level;
import org.javacord.api.entity.message.Message;

import java.awt.*;

public class Ban extends Permissionable implements ICommand {
    @Override
    public void onCommand(Main instance, Message message, String[] args) {
        if(!hasPermission(instance, message, this.getClass())){
            new MessageSender("No Permissions", instance.getConfig().getMessage("generic", "no_perms"), Color.RED, message.getChannel());
            return;
        }
        if(message.isPrivateMessage()){
            return;
        }
        message.getMentionedUsers().iterator().forEachRemaining(user -> {
            message.getServer().orElseThrow(NoSuchServerException::new).banUser(user);
            if(!args[args.length - 1].equals("-s")){
                new MessageSender("User banned.", instance.getConfig().getMessage("ban", "success").replace("%_user_%", user.getDiscriminatedName()), Color.BLACK, message.getServer().get().getChannelsByName(instance.getConfig().getCommandRoom(this.getClass().getSimpleName())).get(0).asServerTextChannel().orElseThrow(NoSuchServerTextChannelException::new));
            }
            new Logger(instance, true, "User banned", "User " + user.getDiscriminatedName() + " has been banned from the server.", Level.INFO);
        });
    }

    @Override
    public String getUsage() {
        return "<users (mentions)> (-s (silent?))";
    }
}
