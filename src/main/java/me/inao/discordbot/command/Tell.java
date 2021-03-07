package me.inao.discordbot.command;

import me.inao.discordbot.Main;
import me.inao.discordbot.ifaces.ICommand;
import me.inao.discordbot.ifaces.IParameter;
import org.javacord.api.entity.message.Message;

import java.util.List;

public class Tell implements ICommand {

    @Override
    public void onCommand(Main instance, Message message, List<IParameter> args) {

    }

    @Override
    public String getUsage() {
        return "<channel name> <message>";
    }

    @Override
    public Class<? extends IParameter>[] requiredParameters() {
        return new Class[0];
    }
}
