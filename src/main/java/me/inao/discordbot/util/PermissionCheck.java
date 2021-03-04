package me.inao.discordbot.util;

import lombok.AllArgsConstructor;
import me.inao.discordbot.Main;
import org.javacord.api.entity.permission.Permissions;
import org.javacord.api.entity.server.Server;
import org.javacord.api.entity.user.User;

@AllArgsConstructor
public class PermissionCheck{
    private final Main main;

    public boolean hasPermission(Server server, User user, String command){
        if(Integer.parseInt(main.getConfig().getCommandPerms(command)) == 0){
            return true;
        }
//        System.out.println(server.hasPermission(user, Permissions.fromBitmask(Integer.parseInt(main.getConfig().getCommandPerms(command))).getAllowedPermission().iterator().next()));
        return server.hasPermission(user, Permissions.fromBitmask(Integer.parseInt(main.getConfig().getCommandPerms(command))).getAllowedPermission().iterator().next());
    }
}
