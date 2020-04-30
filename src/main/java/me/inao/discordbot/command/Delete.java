package me.inao.discordbot.command;

import me.inao.discordbot.Main;
import me.inao.discordbot.ifaces.ICommand;
import me.inao.discordbot.ifaces.Permissionable;
import me.inao.discordbot.util.PermissionCheck;
import org.javacord.api.entity.message.Message;
import org.javacord.api.entity.message.MessageSet;

public class Delete extends Permissionable implements ICommand {
    @Override
    public void onCommand(Main instance, Message message, String[] args) {
        if(!hasPermission(instance, message, this.getClass())){
            message.getChannel().sendMessage("Sorry, not enough perms! :(");
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
