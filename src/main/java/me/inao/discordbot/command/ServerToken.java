package me.inao.discordbot.command;

import me.inao.discordbot.Main;
import me.inao.discordbot.annotation.Permission;
import me.inao.discordbot.ifaces.ICommand;
import me.inao.discordbot.ifaces.IParameter;
import org.javacord.api.entity.message.Message;

import java.util.List;

public class ServerToken implements ICommand{

    @Override
    @Permission
    public void onCommand(Main instance, Message message, List<IParameter> args) {
        if(!(Boolean.parseBoolean(instance.getConfig().getServerProperty("enabled")))){
            System.out.println("here");
        }
    }

    @Override
    public String getUsage() {
        return null;
    }

    @Override
    public Class<? extends IParameter>[] requiredParameters() {
        return new Class[0];
    }
}
