package me.inao.discordbot.command;

import me.inao.discordbot.Main;
import me.inao.discordbot.commands.params.UserParam;
import me.inao.discordbot.ifaces.ICommand;
import me.inao.discordbot.ifaces.IParameter;
import org.javacord.api.entity.message.Message;

import java.util.List;

public class Test implements ICommand {
    @Override
    public void onCommand(Main instance, Message message, List<IParameter> arguments) {
        message.getChannel().sendMessage("test command..");
    }

    @Override
    public String getUsage() {
        return null;
    }

    @Override
    public Class<? extends IParameter>[] requiredParameters() {
        return new Class[]{ UserParam.class };
    }

}
