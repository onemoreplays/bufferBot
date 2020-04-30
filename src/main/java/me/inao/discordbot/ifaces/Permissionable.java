package me.inao.discordbot.ifaces;

import me.inao.discordbot.Main;
import me.inao.discordbot.util.PermissionCheck;
import org.javacord.api.entity.message.Message;

public class Permissionable {
    public boolean hasPermission(Main main, Message message, Class<? extends ICommand> command){
        return new PermissionCheck(main).hasPermission(message.getServer().get(), message.getAuthor().asUser().get(), command.getSimpleName());
    }
}
