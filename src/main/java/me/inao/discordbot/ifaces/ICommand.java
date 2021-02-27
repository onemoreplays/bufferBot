package me.inao.discordbot.ifaces;

import me.inao.discordbot.Main;
import org.javacord.api.entity.message.Message;

import java.util.List;

public interface ICommand {

    void onCommand(Main instance, Message message, List<IParameter> args);
    String getUsage();
    Class<? extends IParameter>[] requiredParameters();
}
