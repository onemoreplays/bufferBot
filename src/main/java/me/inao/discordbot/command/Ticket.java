package me.inao.discordbot.command;

import me.inao.discordbot.Main;
import me.inao.discordbot.ifaces.ICommand;
import me.inao.discordbot.ifaces.Permissionable;
import org.javacord.api.entity.message.Message;

public class Ticket extends Permissionable implements ICommand {
    @Override
    public void onCommand(Main instance, Message message, String[] args) {

    }

    @Override
    public String getUsage() {
        return "<reason>";
    }
}
