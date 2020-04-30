package me.inao.discordbot.command;

import me.inao.discordbot.Main;
import me.inao.discordbot.ifaces.ICommand;
import me.inao.discordbot.ifaces.Permissionable;
import me.inao.discordbot.util.MessageSender;
import me.inao.discordbot.util.PermissionCheck;
import org.javacord.api.entity.message.Message;
import org.javacord.api.entity.message.MessageSet;

import java.awt.*;

public class Delete extends Permissionable implements ICommand {
    @Override
    public void onCommand(Main instance, Message message, String[] args) {
        if(!hasPermission(instance, message, this.getClass())){
            new MessageSender("No Permissions", instance.getConfig().getMessage("generic", "no_perms"), Color.RED, message.getChannel());
            return;
        }
        if(args == null || args.length < 1){
            message.getChannel().sendMessage("Not enough args! :angry:");
            return;
        }
        message.getChannel().getMessages(Integer.parseInt(args[0]) + 1).thenAcceptAsync(MessageSet::deleteAll);
    }

    @Override
    public String getUsage() {
        return "<count>";
    }
}
