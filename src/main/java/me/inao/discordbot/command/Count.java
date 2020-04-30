package me.inao.discordbot.command;

import me.inao.discordbot.Main;
import me.inao.discordbot.ifaces.ICommand;
import me.inao.discordbot.ifaces.Permissionable;
import me.inao.discordbot.objects.Countgame;
import me.inao.discordbot.util.ExceptionCatcher;
import me.inao.discordbot.util.MessageSender;
import me.inao.discordbot.util.PermissionCheck;
import org.javacord.api.entity.message.Message;

import java.awt.*;
import java.util.Arrays;

public class Count extends Permissionable implements ICommand {

    @Override
    public void onCommand(Main instance, Message message, String[] args) {
        if(!hasPermission(instance, message, this.getClass())){
            new MessageSender("No Permissions", instance.getConfig().getMessage("generic", "no_perms"), Color.RED, message.getChannel());
            return;
        }
        if(args.length < 1){
            return;
        }
        long finish = 1337;
        long prev = 0;
        try{
            finish = Long.decode(args[0]);
            if(args.length == 2){
                prev = Long.decode(args[1]);
            }
        }catch (Exception e){
            new ExceptionCatcher(e);
        }
        if(finish < 1){
            return;
        }
        if(instance.getCountgame() != null){
            return;
        }
        long finalFinish = finish;
        long prevFinish = prev;
        message.getServer().ifPresent(server-> instance.setCountgame(new Countgame(server.getChannelsByName(instance.getConfig().getCommandRoom(this.getClass().getSimpleName())).get(0).asServerTextChannel().get(), finalFinish, instance, prevFinish)));

    }

    @Override
    public String getUsage() {
        return "<finish> (prev)";
    }
}
