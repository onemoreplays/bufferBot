package me.inao.discordbot.command.punishment;

import me.inao.discordbot.Main;
import me.inao.discordbot.commands.params.SilentParam;
import me.inao.discordbot.commands.params.UserParam;
import me.inao.discordbot.exception.NoSuchServerException;
import me.inao.discordbot.ifaces.ICommand;
import me.inao.discordbot.ifaces.IParameter;
import me.inao.discordbot.util.ParamsUtil;
import org.javacord.api.entity.message.Message;
import org.javacord.api.entity.user.User;

import java.util.List;

public class Ban implements ICommand {
    @Override
    public void onCommand(Main instance, Message message, List<IParameter> args) {
        if (!instance.getPermissionable().checkPermission(message, this.getClass()))  return;
        boolean silent = false;
        List<User> users = ((UserParam) ParamsUtil.filterObject(UserParam.class, args).get(0)).getParsedUsers();
        if (users != null) {
            users.forEach(user -> {
                message.getServer().orElseThrow(NoSuchServerException::new).banUser(user);
            });
        }
    }

    @Override
    public String getUsage() {
        return "<users (mentions)> (-s (silent?))";
    }

    @Override
    public Class<? extends IParameter>[] requiredParameters() {
        return new Class[]{
                UserParam.class,
                SilentParam.class
        };
    }
}
