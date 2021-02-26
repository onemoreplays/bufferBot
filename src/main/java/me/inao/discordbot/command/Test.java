package me.inao.discordbot.command;

import me.inao.discordbot.Main;
import me.inao.discordbot.ifaces.ICommand;
import org.javacord.api.entity.message.Message;

public class Test implements ICommand {
    @Override
    public void onCommand(Main instance, Message message, String[] args) {
        message.getChannel().sendMessage("test command..");
    }

    @Override
    public String getUsage() {
        return null;
    }
}
