package me.inao.discordbot.command;

import me.inao.discordbot.Main;
import me.inao.discordbot.commands.params.SilentParam;
import me.inao.discordbot.commands.params.UserParam;
import me.inao.discordbot.ifaces.ICommand;
import me.inao.discordbot.ifaces.IParameter;
import org.javacord.api.entity.message.Message;

import java.util.List;

public class Test implements ICommand {
    @Override
    public void onCommand(Main instance, Message message, List<IParameter> arguments) {
        boolean silent = false;
        for (IParameter parameter : arguments){
            if(parameter instanceof SilentParam){
                silent = ((SilentParam) parameter).isProvided();
            }
        }
        if(!silent){
            message.getChannel().sendMessage("test command..");
        }
    }

    @Override
    public String getUsage() {
        return null;
    }

    @Override
    public Class<? extends IParameter>[] requiredParameters() {
        return new Class[]{ UserParam.class, SilentParam.class };
    }

}
