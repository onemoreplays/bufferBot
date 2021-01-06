package me.inao.discordbot.command;

import me.inao.discordbot.Main;
import me.inao.discordbot.ifaces.ICommand;
import me.inao.discordbot.ifaces.Permissionable;
import me.inao.discordbot.server.KeyExchange;
import org.javacord.api.entity.message.Message;

public class Test extends Permissionable implements ICommand {
    @Override
    public void onCommand(Main instance, Message message, String[] args) {
//        Packet packet = new Packet();
//        packet.setAction("create");
//        packet.setUser(message.getUserAuthor().get().getIdAsString());
//        new CaptchaRequest().onRequest("http://127.0.0.1/endpoint/captcha", packet);
        new KeyExchange();
    }

    @Override
    public String getUsage() {
        return null;
    }
}
