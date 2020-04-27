package me.inao.discordbot.command;

import me.inao.discordbot.Main;
import me.inao.discordbot.ifaces.ICommand;
import me.inao.discordbot.util.PermissionCheck;
import org.javacord.api.entity.message.Message;
import org.javacord.api.entity.message.MessageSet;

public class Delete implements ICommand {
    @Override
    public void onCommand(Main instance, Message message, String[] args) {
        if(!(hasPermission(instance, message))){
            message.getChannel().sendMessage("Sorry, not enough perms! :(");
            return;
        }
        if(args == null || args.length < 1){
            message.getChannel().sendMessage("Not enough args! :angry:");
            return;
        }
        message.getChannel().getMessages(Integer.parseInt(args[0])).thenAcceptAsync(MessageSet::deleteAll);
    }

    @Override
    public String getUsage() {
        return "<count>";
    }

    @Override
    public boolean hasPermission(Main main, Message message) {
        return new PermissionCheck(main).hasPermission(message.getServer().get(), message.getAuthor().asUser().get(), this.getClass().getSimpleName());
    }
}
