package me.inao.discordbot.command;

import me.inao.discordbot.Main;
import me.inao.discordbot.exception.NoSuchServerException;
import me.inao.discordbot.exception.NoSuchServerTextChannelException;
import me.inao.discordbot.exception.NoSuchUserException;
import me.inao.discordbot.ifaces.ICommand;
import me.inao.discordbot.util.MessageSender;
import me.inao.discordbot.util.PermissionCheck;
import org.javacord.api.entity.message.Message;

import java.awt.*;

public class Ban implements ICommand {
    @Override
    public void onCommand(Main instance, Message message, String[] args) {
        if(!hasPermission(instance, message)){
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
        });
    }

    @Override
    public String getUsage() {
        return "<users (mentions)> (-s (silent?))";
    }

    @Override
    public boolean hasPermission(Main main, Message message) {
        return new PermissionCheck(main).hasPermission(message.getServer().orElseThrow(NoSuchServerException::new), message.getAuthor().asUser().orElseThrow(NoSuchUserException::new), this.getClass().getSimpleName());
    }
}
