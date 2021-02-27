package me.inao.discordbot.command.util;

import me.inao.discordbot.Main;
import me.inao.discordbot.ifaces.ICommand;
import me.inao.discordbot.ifaces.IParameter;
import org.javacord.api.entity.message.Message;

import java.util.List;

public class Delete implements ICommand {
    @Override
    public void onCommand(Main instance, Message message, List<IParameter> args) {
//        if(!instance.getPermissionable().hasPermission(message, this.getClass())){
//            new MessageSender("No Permissions", instance.getConfig().getMessage("generic", "no_perms"), Color.RED, message.getChannel());
//            return;
//        }
//        if(args == null || args.size() < 1){
//            new MessageSender("Not enough arguments", instance.getConfig().getMessage("generic", "no_args"), Color.RED, message.getChannel());
//            return;
//        }
//        message.getChannel().getMessages(Integer.parseInt(args[0]) + 1).thenAcceptAsync(MessageSet::deleteAll);
    }

    @Override
    public String getUsage() {
        return "<count>";
    }

    @Override
    public Class<? extends IParameter>[] requiredParameters() {
        return new Class[0];
    }
}
