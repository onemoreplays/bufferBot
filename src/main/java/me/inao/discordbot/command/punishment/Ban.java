package me.inao.discordbot.command.punishment;

import me.inao.discordbot.Main;
import me.inao.discordbot.ifaces.ICommand;
import me.inao.discordbot.ifaces.IParameter;
import org.javacord.api.entity.message.Message;

import java.util.List;

public class Ban implements ICommand {
    @Override
    public void onCommand(Main instance, Message message, List<IParameter> args) {
//        if(instance.getPermissionable().hasPermission(message, this.getClass())){
//            new MessageSender("No Permissions", instance.getConfig().getMessage("generic", "no_perms"), Color.RED, message.getChannel());
//            return;
//        }
//        if(message.isPrivateMessage()){
//            return;
//        }
//        message.getMentionedUsers().iterator().forEachRemaining(user -> {
//            message.getServer().orElseThrow(NoSuchServerException::new).banUser(user);
//            if(!args.get(0).equals("-s")){
//                new MessageSender("User banned.", instance.getConfig().getMessage("ban", "success").replace("%_user_%", user.getDiscriminatedName()), Color.BLACK, message.getServer().get().getChannelsByName(instance.getConfig().getCommandRoom(this.getClass().getSimpleName())).get(0).asServerTextChannel().orElseThrow(NoSuchServerTextChannelException::new));
//            }
//            new Logger(instance, true, true, "User banned", "User " + user.getDiscriminatedName() + " has been banned from the server.", Level.INFO);
//        });
    }

    @Override
    public String getUsage() {
        return "<users (mentions)> (-s (silent?))";
    }

    @Override
    public Class<? extends IParameter>[] requiredParameters() {
        return new Class[0];
    }
}
