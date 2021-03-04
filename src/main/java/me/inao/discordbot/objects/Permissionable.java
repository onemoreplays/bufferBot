package me.inao.discordbot.objects;

import lombok.RequiredArgsConstructor;
import me.inao.discordbot.Main;
import me.inao.discordbot.ifaces.ICommand;
import me.inao.discordbot.util.MessageSender;
import me.inao.discordbot.util.PermissionCheck;
import org.javacord.api.entity.message.Message;

import java.awt.*;

@RequiredArgsConstructor
public class Permissionable {
    private final Main main;
    public boolean hasPermission(Message message, Class<? extends ICommand> command){
        return new PermissionCheck(main).hasPermission(message.getServer().get(), message.getAuthor().asUser().get(), command.getSimpleName());
    }
    public boolean checkPermission(Message message, Class<? extends ICommand> command){
        if(!hasPermission(message, command)){
            new MessageSender("No Permissions", main.getConfig().getMessage("generic", "no_perms"), Color.RED, message.getChannel());
            return false;
        }
        return true;
    }
}
