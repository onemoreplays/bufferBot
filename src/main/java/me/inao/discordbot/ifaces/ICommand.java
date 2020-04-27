package me.inao.discordbot.ifaces;

import me.inao.discordbot.Main;
import org.javacord.api.entity.message.Message;

public interface ICommand {

    void onCommand(Main instance, Message message, String[] args);
    String getUsage();
    boolean hasPermission(Main main, Message message);
}
