package me.inao.discordbot.command;

import me.inao.discordbot.Main;
import me.inao.discordbot.exception.NoSuchServerException;
import me.inao.discordbot.exception.NoSuchUserException;
import me.inao.discordbot.ifaces.ICommand;
import me.inao.discordbot.util.PermissionCheck;
import org.javacord.api.entity.message.Message;

public class Tell implements ICommand {
    @Override
    public void onCommand(Main instance, Message message, String[] args) {
        if(!(hasPermission(instance, message))){
            return;
        }
    }

    @Override
    public String getUsage() {
        return "<channel name> <message>";
    }
    @Override
    public boolean hasPermission(Main main, Message message) {
        return new PermissionCheck(main).hasPermission(message.getServer().orElseThrow(NoSuchServerException::new), message.getAuthor().asUser().orElseThrow(NoSuchUserException::new), this.getClass().getSimpleName());
    }
}
