package me.inao.discordbot.ifaces;

import lombok.RequiredArgsConstructor;
import me.inao.discordbot.Main;
import me.inao.discordbot.util.PermissionCheck;
import org.javacord.api.entity.message.Message;

@RequiredArgsConstructor
public class Permissionable {
    private final Main main;
    public boolean hasPermission(Message message, Class<? extends ICommand> command){
        return new PermissionCheck(main).hasPermission(message.getServer().get(), message.getAuthor().asUser().get(), command.getSimpleName());
    }
}
