package me.inao.discordbot.command;

import me.inao.discordbot.Main;
import me.inao.discordbot.annotation.Permission;
import me.inao.discordbot.ifaces.ICommand;
import me.inao.discordbot.ifaces.IParameter;
import me.inao.discordbot.request.http.Captcha;
import me.inao.discordbot.request.http.Driver;
import me.inao.discordbot.util.MessageSender;
import org.javacord.api.entity.message.Message;

import java.awt.*;
import java.util.List;

@Permission
public class Ping implements ICommand {
    @Override
    public void onCommand(Main instance, Message message, List<IParameter> args) {
        Captcha captcha = new Captcha();
        if(instance.getConfig().getFeatureValue("captchaSystem", "httpAuth") != null){
            captcha.getArguments().put("auth", ((String)instance.getConfig().getFeatureValue("captchaSystem", "httpAuth")));
        }
        captcha.getArguments().put("discordId", message.getUserAuthor().get().getIdAsString());
        new Driver(captcha).postRequest();
        new MessageSender("pong", "pong", Color.RED, message.getChannel());
    }

    @Override
    public Class<? extends IParameter>[] requiredParameters() {
        return new Class[0];
    }
}
